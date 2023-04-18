public record Message(int recID, int portID, String payload) {

    static Message from(boolean isBroadcast, int recID, int portID, String payload) {
        // error checking
        // parse int as hexadecimal?
        if (isBroadcast)
            return new Message(Integer.MAX_VALUE, portID, payload);
        else
            return new Message(recID, portID, payload);
    }
}
