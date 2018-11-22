package com.opensource.app.idare.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;

import static com.opensource.app.idare.utils.Constants.DATE_PATTERN;

/**
 *
 */
public class NotificationItem implements Parcelable {

    public static final Creator<NotificationItem> CREATOR = new Creator<NotificationItem>() {
        @Override
        public NotificationItem createFromParcel(Parcel in) {
            return new NotificationItem(in);
        }

        @Override
        public NotificationItem[] newArray(int size) {
            return new NotificationItem[size];
        }
    };
    private String title;
    private String body;
    private String notificationType;
    private String initiatorId;
    private String recipientId;
    private String initiatorName;
    private long timestamp;

    protected NotificationItem(Parcel in) {
        title = in.readString();
        body = in.readString();
        notificationType = in.readString();
        initiatorId = in.readString();
        recipientId = in.readString();
        initiatorName = in.readString();
        timestamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(notificationType);
        dest.writeString(initiatorId);
        dest.writeString(recipientId);
        dest.writeString(initiatorName);
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotificationTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(timestamp);
    }

    @Override
    public String toString() {
        return "NotificationItem{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", initiatorId='" + initiatorId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
