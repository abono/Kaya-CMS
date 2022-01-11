import React, { useReducer } from "react"

export const reducer = (state, action) => {
  switch (action.type) {
    case "CHECKING_LOGGED_IN":
      return {
        ...state,
        errorMessage: null,
        statusMessage: "Checking to see if you are already logged in, please wait...",
        lastCheck: Date.now()
      }
    case "LOGGED_IN":
      return {
        ...state,
        errorMessage: null,
        statusMessage: null,
        user: action.payload,
        lastCheck: Date.now()
      }
    case "LOGGED_OUT":
      return {
        ...state,
        errorMessage: null,
        statusMessage: null,
        user: null,
        lastCheck: Date.now()
      }
    case "ALERT_ERROR":
      return {
        ...state,
        errorMessage: action.payload,
        statusMessage: null,
      }
    case "ALERT_MESSAGE":
      return {
        ...state,
        errorMessage: null,
        statusMessage: action.payload,
      }
    case "ALERT_CLOSE":
      return {
        ...state,
        errorMessage: null,
        statusMessage: null,
      }
    default:
      throw new Error("Invalid action type " + action.type);
  }
}

export const initialState = {
  user: null,
  errorMessage: null,
  statusMessage: null,
  lastCheck: 0
}

export const UserContext = React.createContext({
  state: initialState,
  dispatch: () => null
})

export const UserProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState)

  return (
    <UserContext.Provider value={[ state, dispatch ]}>
    	{ children }
    </UserContext.Provider>
  )
}
