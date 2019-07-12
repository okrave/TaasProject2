package com.example.togroup5.demo.entities.payloadsResults;

public class MemberGroupPayload extends UserIDName {
    private Long groupId;

    //

    public MemberGroupPayload(){}

    public MemberGroupPayload(Long userId, String userName, Long groupId) {
        super(userId, userName);
        this.groupId = groupId;
    }

    //

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "MemberGroupPayload{" + super.toString() +
                "groupId=" + groupId +
                '}';
    }
}
