package otus.objects;

public class MessageBody {
    private final Attachment attachment;

    public MessageBody(Attachment attachment) {
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
