public record Message(Motherboard motherboard, int recID, int portID, String payload) {

    static Message from(boolean isBroadcast, Motherboard motherboard, int recID, int portID, String payload) {
        // error checking
        // parse int as hexadecimal?
        if (isBroadcast)
            return new Message(motherboard, Integer.MAX_VALUE, portID, payload);
        else
            return new Message(motherboard, recID, portID, payload);
    }
}
