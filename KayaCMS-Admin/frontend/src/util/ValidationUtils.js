export const validateEmail = (email) => {
  return String(email)
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};

export const VALID_PASSWORD_DESCRIPTION = "contain at least one upper case letter, one lower case letter, a number and a special character";

export const validatePassword = (password) => {
  // Got this regex from https://www.thepolyglotdeveloper.com/2015/05/use-regex-to-test-password-strength-in-javascript/
  // and then tweaked the "special character" part to mean any non-alphanumeric (so space, underscore, dash, equal, etc.)
  var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=.{8,})");
  return strongRegex.test(password);
};
