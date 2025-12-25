package ecomhub.cart.event.model;

public class ResourceEvent {

    private String id;
    private String action;
    private String data;

    public ResourceEvent() {
    }

    public ResourceEvent(String id, String action, String data) {
        this.id = id;
        this.action = action;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResourceEvent{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
