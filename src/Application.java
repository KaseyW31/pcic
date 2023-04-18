public interface Application {

    Motherboard getMotherboard();

    void send(boolean isBroadcast, int recID, int portID, String payload);

    void receive(Message message);
}
