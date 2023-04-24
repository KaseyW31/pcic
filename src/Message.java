/**
 * This record represents a message that can be sent between applications connected to devices on a motherboard.
 * A message contains an ID for the recipient device as mapped on the motherboard (recID), an ID associated with the
 * port of the receiving application on the device (portID), and the contents of the message (payload).
 * A message is sent using the send method of Application. The delegation process is:
 * Sending application --> Sending device --> Motherboard --> Receiving device --> Receiving application
 */
public record Message(int recID, int portID, String payload) {

    /* A special recID that denotes that this message is to be sent to matching applications on all devices
    connected to the motherboard */
    public static final int BROADCAST_IDENTIFIER = Integer.MAX_VALUE;
}
