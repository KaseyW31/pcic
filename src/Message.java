public record Message(int recID, int portID, String payload) {

    static Message from(boolean isBroadcast, int recID, int portID, String payload) {
        assert payload != null;
        if (isBroadcast)
            return new Message(Integer.MAX_VALUE, portID, payload);
        else
            return new Message(recID, portID, payload);
    }
}
