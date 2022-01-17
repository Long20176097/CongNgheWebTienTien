const rootAPI = process.env.REACT_APP_SERVER_URL;

const request = (options) => {
  const headers = new Headers();

  if (options.setContentType !== false) {
    headers.append("Content-Type", "application/json");
  }

  if (localStorage.getItem("jwttoken")) {
    headers.append(
      "Authorization",
      localStorage.getItem("jwttoken")
    );
  }

  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then((response) =>
    response.json().then((json) => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};


// export function facebookLogin(facebookLoginRequest) {
//   return request({
//     url: rootAPI + "/facebook/signin",
//     method: "POST",
//     body: JSON.stringify(facebookLoginRequest),
//   });
// }

// export function signup(signupRequest) {
//   return request({
//     url: rootAPI + "/users",
//     method: "POST",
//     body: JSON.stringify(signupRequest),
//   });
// }

export function getCurrentUser() {
  if (!localStorage.getItem("jwttoken")) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: rootAPI + "/users/me",
    method: "GET",
  });
}

export function getUsers() {
  if (!localStorage.getItem("jwttoken")) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: rootAPI + "/users/summaries",
    method: "GET",
  });
}

export function countNewMessages(senderId, recipientId) {
  if (!localStorage.getItem("jwttoken")) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: rootAPI + "/messages/" + senderId + "/" + recipientId + "/count",
    method: "GET",
  });
}

export function findChatMessages(senderId, recipientId) {
  if (!localStorage.getItem("jwttoken")) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: rootAPI + "/messages/" + senderId + "/" + recipientId,
    method: "GET",
  });
}

export function findChatMessage(id) {
  if (!localStorage.getItem("jwttoken")) {
    return Promise.reject("No access token set.");
  }

  return request({
    url: rootAPI + "/messages/" + id,
    method: "GET",
  });
}
