export const APICallInit = {
  isLoading: false,
  isError: false,
  errorMessage: '',
  data: null
}

export const APICallState = (state, action) => {
  console.log("API Call State received", action.type, action.payload, state);
  switch (action.type) {
    case 'FETCH_INIT':
      return {
        ...state,
        isLoading: true,
        isError: false,
        errorMessage: '',
      };
    case 'FETCH_SUCCESS':
      return {
        ...state,
        isLoading: false,
        isError: false,
        errorMessage: '',
        data: action.payload,
      };
    case 'FETCH_FAILURE':
      return {
        ...state,
        isLoading: false,
        isError: true,
        errorMessage: action.payload,
      };
    default:
      throw new Error("Data type " + action.type + " not valid.");
  }
};

export const APIGet = (url, dispatch) => {
  const fetchData = async () => {
    dispatch({ type: 'FETCH_INIT' });

    fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      console.log("Received check user response", response);
      // Unfortunately, fetch doesn't send (404 error) into the cache itself
      // You have to send it, as I have done below
      if (response.status >= 400 && response.ok) {
        console.log("WARNING: Why is status " + response.status + " but it is OK?");
      }
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text); });
      } else {
        return response.text();
      }
    })
    .then(dataString => {
      return dataString ? JSON.parse(dataString) : null;
    })
    .then(data => {
      dispatch({
        type: 'FETCH_SUCCESS',
        payload: data
      });
    })
    .catch((error) => {
      let messageString = error.message;
      let message = messageString;
      try {
        let errorJSON = JSON.parse(messageString);
        message = errorJSON.message ? errorJSON.message : message;
      } catch (jsonParseError) {
        // Leave the message as the messageString
      }
      dispatch({ type: 'FETCH_FAILURE', payload: message });
    });
  };

  fetchData();
}

export const APIPost = (url, body, dispatch) => {
  const fetchData = async () => {
    dispatch({ type: 'FETCH_INIT' });

    fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body),
    })
    .then(response => {
      console.log("Received check user response", response);
      // Unfortunately, fetch doesn't send (404 error) into the cache itself
      // You have to send it, as I have done below
      if (response.status >= 400 && response.ok) {
        console.log("WARNING: Why is status " + response.status + " but it is OK?");
      }
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text); });
      } else {
        return response.text();
      }
    })
    .then(dataString => {
      return dataString ? JSON.parse(dataString) : null;
    })
    .then(data => {
      dispatch({
        type: 'FETCH_SUCCESS',
        payload: data
      });
    })
    .catch((error) => {
      let messageString = error.message;
      let message = messageString;
      try {
        let errorJSON = JSON.parse(messageString);
        message = errorJSON.message ? errorJSON.message : message;
      } catch (jsonParseError) {
        // Leave the message as the messageString
      }
      dispatch({ type: 'FETCH_FAILURE', payload: message });
    });
  };

  fetchData();
}

export const APIPut = (url, body, dispatch) => {
  const fetchData = async () => {
    dispatch({ type: 'FETCH_INIT' });

    fetch(url, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(body),
    })
    .then(response => {
      console.log("Received check user response", response);
      // Unfortunately, fetch doesn't send (404 error) into the cache itself
      // You have to send it, as I have done below
      if (response.status >= 400 && response.ok) {
        console.log("WARNING: Why is status " + response.status + " but it is OK?");
      }
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text); });
      } else {
        return response.text();
      }
    })
    .then(dataString => {
      return dataString ? JSON.parse(dataString) : null;
    })
    .then(data => {
      dispatch({
        type: 'FETCH_SUCCESS',
        payload: data
      });
    })
    .catch((error) => {
      let messageString = error.message;
      let message = messageString;
      try {
        let errorJSON = JSON.parse(messageString);
        message = errorJSON.message ? errorJSON.message : message;
      } catch (jsonParseError) {
        // Leave the message as the messageString
      }
      dispatch({ type: 'FETCH_FAILURE', payload: message });
    });
  };

  fetchData();
}

export const APIDelete = (url, dispatch) => {
  const fetchData = async () => {
    dispatch({ type: 'FETCH_INIT' });

    fetch(url, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
    })
    .then(response => {
      console.log("Received check user response", response);
      // Unfortunately, fetch doesn't send (404 error) into the cache itself
      // You have to send it, as I have done below
      if (response.status >= 400 && response.ok) {
        console.log("WARNING: Why is status " + response.status + " but it is OK?");
      }
      if (!response.ok) {
        return response.text().then(text => { throw new Error(text); });
      } else {
        return response.text();
      }
    })
    .then(dataString => {
      return dataString ? JSON.parse(dataString) : null;
    })
    .then(data => {
      dispatch({
        type: 'FETCH_SUCCESS',
        payload: data
      });
    })
    .catch((error) => {
      let messageString = error.message;
      let message = messageString;
      try {
        let errorJSON = JSON.parse(messageString);
        message = errorJSON.message ? errorJSON.message : message;
      } catch (jsonParseError) {
        // Leave the message as the messageString
      }
      dispatch({ type: 'FETCH_FAILURE', payload: message });
    });
  };

  fetchData();
}

