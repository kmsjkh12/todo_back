import React, { useEffect, useState, useRef } from 'react';

interface WebSocketChatProps {
    teamId: string; // Team ID to connect to the correct WebSocket channel
    chat :boolean;
    setChat: React.Dispatch<React.SetStateAction<boolean>>; // Matches the state setter type
}

const WebSocketChat: React.FC<WebSocketChatProps> = ({ teamId,chat, setChat }) => {
    const [messages, setMessages] = useState<{ senderNickname: string; data: string }[]>([]);
    const [messageInput, setMessageInput] = useState<string>('');
    const ws = useRef<WebSocket | null>(null); // WebSocket reference

    useEffect(() => {
        if (!teamId) {return};

        
        // Initialize WebSocket connection
        ws.current = new WebSocket(`ws://localhost:8080/room?teamid=${teamId}`);

        ws.current.onmessage = (event) => {
            console.log(event);
            const message = JSON.parse(event.data);
            // Extract senderNickname and data
            if (message.senderNickname && message.data) {
                setMessages((prevMessages) => [
                    ...prevMessages,
                    { senderNickname: message.senderNickname, data: message.data },
                ]);
            }
        };

      
        return () =>{
            setMessages([])
        }
    }, [teamId]);
    const handleSendMessage = () => {
        if (ws.current && messageInput.trim()) {
            ws.current.send(messageInput);
            setMessageInput('');
        }
    };
    

    const handleCloseChat = () => {
        ws.current?.close();
        setChat(false);
    };

    return (
        <div>
                <div style={styles.chatModal as React.CSSProperties}>
                    <div style={styles.chatHeader as React.CSSProperties}>
                        <h3>Team Chat</h3>
                        <button onClick={handleCloseChat}>Close</button>
                    </div>
                    <div style={styles.chatMessages as React.CSSProperties}>
                        {messages.map((msg, index) => (
                            <div key={index}>
                                <strong>{msg.senderNickname}:</strong> {msg.data}
                            </div>
                        ))}
                    </div>
                    <div style={styles.chatInput as React.CSSProperties}>
                        <input
                            type="text"
                            value={messageInput}
                            onChange={(e) => setMessageInput(e.target.value)}
                            placeholder="Type a message"
                        />
                        <button onClick={handleSendMessage}>Send</button>
                    </div>
                </div>
        </div>
    );
};

const styles: { [key: string]: React.CSSProperties } = {
    chatModal: {
        position: 'fixed',
        bottom: '10px',
        right: '10px',
        width: '300px',
        border: '1px solid #ddd',
        borderRadius: '4px',
        backgroundColor: '#fff',
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
    },
    chatHeader: {
        padding: '10px',
        borderBottom: '1px solid #ddd',
        display: 'flex',
        justifyContent: 'space-between',
    },
    chatMessages: {
        maxHeight: '200px',
        overflowY: 'auto',
        padding: '10px',
    },
    chatInput: {
        padding: '10px',
        borderTop: '1px solid #ddd',
        display: 'flex',
        justifyContent: 'space-between',
    },
};

export default WebSocketChat;
