package otus.objects;

import java.util.Collection;
import java.util.Map;

public class Message {
    private final String title;
    private final MessageBody messageBody;
    private final Collection<Attachment> attachments;
    private final Map<Long, String> map;
    private final Map<Enum, Attachment> map2;
    private final transient String ignoredField;

    public Message(String title, MessageBody messageBody, Collection<Attachment> attachments, Map<Long, String> map, Map<Enum, Attachment> map2, String ignoredField) {
        this.title = title;
        this.messageBody = messageBody;
        this.attachments = attachments;
        this.map = map;
        this.map2 = map2;
        this.ignoredField = ignoredField;
    }

    public String getTitle() {
        return title;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    public Map<Long, String> getMap() {
        return map;
    }

    public Map<Enum, Attachment> getMap2() {
        return map2;
    }

    public String getIgnoredField() {
        return ignoredField;
    }
}
