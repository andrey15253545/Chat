package by.touchsoft.chat.model;


public class Chat {

    private String userId;
    private String companionId;

    public Chat(String agentId, String companionId) {
        this.userId = agentId;
        this.companionId = companionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCompanionId() {
        return companionId;
    }


}
