import { APIGet, APIPost } from './ServiceUtil';

export const LogIn = (userName, password, dispatch) => {
    console.log("Logging in", userName, password);
    let user = {
        userName: userName,
        password: password
    };
    APIPost(`/api/admin/logIn`, user, dispatch);
}

export const LogOut = (dispatch) => {
    console.log("Logging out...");
    APIGet(`/api/admin/logOut`, dispatch);
}

export const GetCurrentUser = (dispatch) => {
    console.log("Checking user...");
    APIGet(`/api/admin/check`, dispatch);
}
