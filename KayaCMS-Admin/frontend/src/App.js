import React from 'react';

import './App.css';

import { UserProvider } from './context/UserContext'

import Template from './pages/Template';

function App() {
  return (
    <UserProvider>
      <Template />
    </UserProvider>
  );
}

export default App;