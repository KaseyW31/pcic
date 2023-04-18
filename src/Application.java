public interface Application {

    Motherboard getMotherboard();

    void send(boolean isBroadcast, int recID, int portID, int payload);

    void receive(String payload);

}
