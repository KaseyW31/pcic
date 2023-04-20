public record Message(int recID, int portID, String payload) {

    public static final int BROADCAST_IDENTIFIER = Integer.MAX_VALUE;
}
