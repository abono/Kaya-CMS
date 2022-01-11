import React, { useReducer, useContext, useEffect } from 'react';
import { HashRouter as Router, Route, Routes } from 'react-router-dom';

import { UserContext } from '../context/UserContext'

import { APICallInit, APICallState } from "../services/ServiceUtil";
import { GetCurrentUser } from "../services/AuthService";

import AppNavbar from '../parts/AppNavbar';

import Home from './Home';
import AdminUserList from './AdminUserList';
import AdminUserEdit from './AdminUserEdit';
import MediaList from './MediaList';
import MediaEdit from "./MediaEdit";
import RedirectList from './RedirectList';
import RedirectEdit from "./RedirectEdit";
import WebPageList from './WebPageList';
import WebPageEdit from "./WebPageEdit";
import WebPageTemplateList from './WebPageTemplateList';
import WebPageTemplateEdit from "./WebPageTemplateEdit";

function Template() {
  const [ userContext, userContextDispatch ] = useContext(UserContext);
  const [ userState, userDispatch ] = useReducer(APICallState, APICallInit);

  useEffect(() => {
    console.log("Checking user context changes", userContext, Date.now() - userContext.lastCheck);
    if (Date.now() - userContext.lastCheck > 5 * 60 * 1000) {
      userContextDispatch( { type: "CHECKING_LOGGED_IN" });
      GetCurrentUser(userDispatch);
    }
  }, [userContext]);

  useEffect(() => {
    console.log("Checking user state changes", userState);
    if (userState.data) {
      userContextDispatch( { type: "LOGGED_IN", payload: userState.data });
    } else {
      userContextDispatch( { type: "LOGGED_OUT" });
    }
  }, [userState]);

  return (
    <div>
      <Router>
        <AppNavbar/>

        {userState.isLoading &&
          <div className="alert alert-warning" role="alert">
            Loading...
          </div>
        }

        {userState.isError &&
          <div className="alert alert-danger" role="alert">
            {userState.errorMessage}
          </div>
        }

        <Routes>
          <Route path={`/`} exact={true} element={<Home />}></Route>
          <Route path={`/adminUser`} exact={true} element={<AdminUserList />}></Route>
          <Route path={`/adminUser/:id`} element={<AdminUserEdit />}></Route>
          <Route path={`/media`} exact={true} element={<MediaList />}></Route>
          <Route path={`/media/:id`} element={<MediaEdit />}></Route>
          <Route path={`/redirect`} exact={true} element={<RedirectList />}></Route>
          <Route path={`/redirect/:id`} element={<RedirectEdit />}></Route>
          <Route path={`/webPage`} exact={true} element={<WebPageList />}></Route>
          <Route path={`/webPage/:id`} element={<WebPageEdit />}></Route>
          <Route path={`/webPageTemplate`} exact={true} element={<WebPageTemplateList />}></Route>
          <Route path={`/webPageTemplate/:id`} element={<WebPageTemplateEdit />}></Route>
        </Routes>
      </Router>
    </div>
  );
}

export default Template;