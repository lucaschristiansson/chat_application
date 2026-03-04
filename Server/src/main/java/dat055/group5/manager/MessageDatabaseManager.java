package dat055.group5.manager;

import dat055.group5.Driver;
import dat055.group5.export.Message;
import dat055.group5.export.MessageManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class MessageDatabaseManager implements MessageManager<List<Message>, Integer> {
    private final dat055.group5.Driver driver;
    private final Connection connection;

    public MessageDatabaseManager(Driver driver){
        this.driver = driver;
        this.connection = driver.getPortalConnection().getConnection();
    }

    @Override
    public boolean addMessage(Message message) {
        String insertMessageSql = "INSERT INTO Messages (username, channel_id, content) VALUES (?, ?, ?)";
        String insertImageSql = "INSERT INTO Images (image_path) VALUES (?)";
        String linkImageToMessageSql = "INSERT INTO ImagesInMessage (message_id, image_id) VALUES (?, ?)";

        String uploadDirectory = "./image_uploads/";

        try {
            connection.setAutoCommit(false);

            int messageId = -1;

            try (PreparedStatement msgStmt = connection.prepareStatement(insertMessageSql, Statement.RETURN_GENERATED_KEYS)) {
                msgStmt.setString(1, message.getSender());
                msgStmt.setInt(2, message.getChannel());
                msgStmt.setString(3, message.getContent());
                msgStmt.executeUpdate();

                try (ResultSet msgKeys = msgStmt.getGeneratedKeys()) {
                    if (msgKeys.next()) {
                        messageId = msgKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated message_id.");
                    }
                }
            }

            List<byte[]> imageBytesList = message.getImageBytes();
            if (imageBytesList != null && !imageBytesList.isEmpty()) {

                Files.createDirectories(Paths.get(uploadDirectory));

                try (PreparedStatement imgStmt = connection.prepareStatement(insertImageSql, Statement.RETURN_GENERATED_KEYS);
                     PreparedStatement linkStmt = connection.prepareStatement(linkImageToMessageSql)) {

                    for (byte[] rawBytes : imageBytesList) {
                        String fileName = UUID.randomUUID() + ".png";
                        String finalPath = uploadDirectory + fileName;

                        Files.write(Paths.get(finalPath), rawBytes);

                        imgStmt.setString(1, finalPath);
                        imgStmt.executeUpdate();

                        int imageId = -1;
                        try (ResultSet imgKeys = imgStmt.getGeneratedKeys()) {
                            if (imgKeys.next()) {
                                imageId = imgKeys.getInt(1);
                            } else {
                                throw new SQLException("Failed to retrieve generated image_id.");
                            }
                        }

                        linkStmt.setInt(1, messageId);
                        linkStmt.setInt(2, imageId);
                        linkStmt.addBatch();
                    }

                    linkStmt.executeBatch();
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Transaction failed. Rolling back changes: " + e.getMessage());
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    @Override
    public List<Message> getMessagesByChannel(Integer channelId) {
        String query =
                "SELECT M.message_id, M.message_time, M.username, M.content, I.image_path " +
                        "FROM Messages M " +
                        "LEFT JOIN ImagesInMessage IM ON M.message_id = IM.message_id " +
                        "LEFT JOIN Images I ON IM.image_id = I.image_id " +
                        "WHERE M.channel_id = ? " +
                        "ORDER BY M.message_time ASC, M.message_id ASC";

        Map<Integer, Message> messageMap = new LinkedHashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, channelId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt("message_id");

                    if (!messageMap.containsKey(messageId)) {
                        Message msg = new Message(
                                rs.getString("username"),
                                rs.getString("content"),
                                rs.getTimestamp("message_time").toInstant(),
                                channelId
                        );
                        messageMap.put(messageId, msg);
                    }

                    String imagePath = rs.getString("image_path");

                    if (imagePath != null) {
                        File imgFile = new File(imagePath);
                        if (imgFile.exists()) {
                            try {
                                byte[] rawBytes = Files.readAllBytes(imgFile.toPath());
                                messageMap.get(messageId).addImageBytes(rawBytes);
                            } catch (Exception fileIOEx) {
                                System.err.println("Failed to read image from disk: " + imagePath);
                            }
                        } else {
                            System.err.println("Warning: Image file missing from disk: " + imagePath);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to load messages: " + e.getMessage());
        }

        return new ArrayList<>(messageMap.values());
    }
}