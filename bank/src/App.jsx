import React, { useState, useEffect } from 'react';

function App() {
  // useState hook to manage a list of items
  const [items, setItems] = useState([]);
  const [message, setMessage] = useState('');

  // useEffect hook to update the message when items change
  useEffect(() => {
    setMessage(`You have ${items.length} items in your list.`);
  }, [items]);

  const addItem = () => {
    const newItem = `Item ${items.length + 1}`;
    setItems([...items, newItem]);
  };

  return (
    <div>
      <p>{message}</p>
      <button onClick={addItem}>Add Item</button>
      <ul>
        {items.map((item, index) => (
          <li key={index}>{item}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;
