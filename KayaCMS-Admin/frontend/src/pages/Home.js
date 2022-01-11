import React, { useContext } from 'react';

import { UserContext } from '../context/UserContext'

import Login from './Login';

function Home() {
    const [ userContext, ] = useContext(UserContext);

    return (
        <div>
            {!userContext.user && (
                <Login />
            )}
        </div>
    );
}

export default Home;