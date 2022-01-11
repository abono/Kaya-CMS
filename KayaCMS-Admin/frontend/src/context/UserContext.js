import React, { useReducer } from "react"

export const reducer = (state, action) => {
  switch (action.type) {
    case "CHECKING_LOGGED_IN":
      return {
        ...state,
        lastCheck: Date.now()
      }
    case "LOGGED_IN":
      return {
        ...state,
        user: action.payload,
        lastCheck: Date.now()
      }
    case "LOGGED_OUT":
      return {
        ...state,
        user: null,
        lastCheck: Date.now()
      }
    default:
      throw new Error("Invalid action type " + action.type);
  }
}

export const initialState = {
  user: null,
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
