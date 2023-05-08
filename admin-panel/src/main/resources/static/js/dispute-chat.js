  const chat = document.getElementById('chat');
  const allTabs = new Array();
  let currentFilter = "all";

  var clientWebSocket = new WebSocket("ws://localhost:4230/api/v1/dispute");
  clientWebSocket.onopen = function() {
      console.log("clientWebSocket.onopen", clientWebSocket);
      console.log("clientWebSocket.readyState", "websocketstatus");
  }
  clientWebSocket.onclose = function(error) {
      console.log("clientWebSocket.onclose", clientWebSocket, error);
  }
  clientWebSocket.onerror = function(error) {
      console.log("clientWebSocket.onerror", clientWebSocket, error);
  }
  clientWebSocket.onmessage = function(message) {
      receiveMessage(message.data)
  }

  function receiveMessage(data) {
      let json = JSON.parse(data);
      if(json.type == 'channel') {
          updateTab(json.object.id, json.object.name, json.object.unread);
          createChatScreen(json.object.id, json.object.name);
      } else if(json.type == 'message') {
          createChatScreen(json.object.channelId, "");
          let chatScreen = document.querySelector(`#tab-thread-content-${json.object.channelId} [class^=card-body]`);
          let adminMessage = `<div class="d-flex chat-message justify-content-end my-1">
                                <div class="flex-column chat-message-content me-2">
                                    <div class="mb-1 message-content-admin rounded-5 p-3 text-white">
                                        <p class="mb-0">${json.object.message.replace(/\n/g, "<br />")}</p>
                                    </div>
                                    <p class="mb-0 ms-7" style="text-align: right !important;">
                                        <b>${json.object.sender},</b> <span class="secondary-text">${json.object.date}</span>
                                    </p>
                                </div>
                             </div>`;
          let leftMessage = `<div class="d-flex chat-message justify-content-start my-3">
                                <div class="flex-column chat-message-content ms-2">
                                    <div class="mb-1 message-content-left border rounded-5 p-3">
                                        <p class="mb-0">${json.object.message.replace(/\n/g, "<br />")}</p>
                                    </div>
                                    <p class="mb-0 ms-7">
                                        <b>${json.object.sender},</b> <span class="secondary-text">${json.object.date}</span>
                                    </p>
                                </div>
                             </div>`;
          if(json.object.sender == 'Admin') {
               chatScreen.innerHTML += adminMessage;
          } else {
               chatScreen.innerHTML += leftMessage;
          }
          scrollChatScreen(json.object.channelId);
      }
  }

  function createChatScreen(threadId, name) {
     let tabThread = document.querySelector('#tab-thread-content-' + threadId);
     if(!tabThread) {
         let tabThreadContent =
                `<div id="tab-thread-content-${threadId}" class="card chat-content fade">
                    <div class="card-header p-3 d-flex justify-content-between">
                        <h5 style="margin-bottom: 0">${name}</h5>
                        <div class="d-flex">
                            <button class="btn btn-primary btn-sm me-1" style="padding-top: 0.1rem; border-radius: 0.4rem;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 1 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
                            </button>
                            <button class="btn btn-primary btn-sm" style="padding-top: 0; border-radius: 0.4rem;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16px" height="16px" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polygon points="7.86 2 16.14 2 22 7.86 22 16.14 16.14 22 7.86 22 2 16.14 2 7.86 7.86 2"></polygon><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>
                            </button>
                        </div>
                    </div>
                    <div class="card-body p-3 d-flex flex-column overflow-auto">
                    </div>
                    <div class="card-footer bg-transparent">
                        <div class="chat-textarea mb-1" placeholder="Type your message..." contenteditable="true"></div>
                        <div class="d-flex justify-content-end align-items-end">
                            <div>
                                <button onclick="sendMessage(${threadId})" type="button" class="btn btn-primary btn-sm px-3">
                                    <svg class="me-1" width="12px" height="12px" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="paper-plane" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" data-fa-i2svg=""><path fill="currentColor" d="M511.6 36.86l-64 415.1c-1.5 9.734-7.375 18.22-15.97 23.05c-4.844 2.719-10.27 4.097-15.68 4.097c-4.188 0-8.319-.8154-12.29-2.472l-122.6-51.1l-50.86 76.29C226.3 508.5 219.8 512 212.8 512C201.3 512 192 502.7 192 491.2v-96.18c0-7.115 2.372-14.03 6.742-19.64L416 96l-293.7 264.3L19.69 317.5C8.438 312.8 .8125 302.2 .0625 289.1s5.469-23.72 16.06-29.77l448-255.1c10.69-6.109 23.88-5.547 34 1.406S513.5 24.72 511.6 36.86z"></path></svg>
                                    Send
                                </button>
                            </div>
                        </div>
                    </div>
                </div>`;

         chat.innerHTML += tabThreadContent;
     }

     if(name != "") {
        tabThread = document.querySelector('#tab-thread-content-' + threadId + ' h5').textContent=name;
     }
  }

  function updateTab(threadId, name, unread) {

     if(allTabs.filter(element => element.querySelector('a').getAttribute('href') == "#tab-thread-content-" + threadId).length == 0) {
         createTab(threadId, name, unread)
     } else {
         updateTabBadge(threadId, unread);
     }
  }

  function createTab(threadId, name, unread) {
       let tabContent =
            `<li class="nav-item my-1">
                <a data-bs-toggle="tab" data-chat-thread="data-chat-thread" href="#tab-thread-content-${threadId}"
                   role="tab"
                   aria-selected="true"
                   class="${unread > 0 ? 'unread' : 'read'} nav-link nav-link-item d-flex align-items-center justify-content-center"
                   onclick = "onTabClick(${threadId}, '${name}')">
                    <div class="flex-grow-1 d-sm-none d-xl-block">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="primary-text">${name}</h5>
                            <p class="secondary-text">Just now</p>
                        </div>
                        <div class="d-flex justify-content-between">
                            <p class="secondary-text">This is a message from
                                you</p>
                            ${unread > 0 ? '<span class="badge badge-phoenix px-1">' + unread + '+</span>' : ''}
                        </div>
                    </div>
                </a>
            </li>`;

       document.getElementById('tabs').innerHTML += tabContent;
       let tab = document.querySelector(`[href="#tab-thread-content-${threadId}"]`);

       allTabs.push(tab.parentNode);
       filterTabs();
  }

  function updateTabBadge(threadId, unread) {
      let tab = document.querySelector(`[href="#tab-thread-content-${threadId}"]`);
      if(unread > 0) {
          allTabs.filter(element => element.querySelector('a').getAttribute('href') == "#tab-thread-content-" + threadId)
                  .map(el => el.childNodes[1])
                  .forEach(el => {
                      if (el.classList.contains('read')) {
                          el.classList.remove('read');
                          el.classList.add('unread');
                          el.querySelector('div div').innerHTML += '<span class="badge badge-phoenix px-1">' + unread + '+</span>';
                      }
                  });
          filterTabs();
      } else {
          allTabs.filter(element => element.querySelector('a').getAttribute('href') == "#tab-thread-content-" + threadId)
                  .map(el => el.childNodes[1])
                  .forEach(el => {
                      if (el.classList.contains('unread')) {
                          el.classList.remove('unread');
                          el.classList.add('read');
                          el.querySelector('div div span').remove();
                      }
                  });
          if(tab.classList.contains('unread')) {
               tab.querySelector('div div span').remove();
          }
      }
  }

  function onTabClick(threadId, name) {
     scrollChatScreen(threadId);
     sendChannel(threadId, name);
  }

  function scrollChatScreen(threadId) {
     let chatScreen = document.querySelector(`#tab-thread-content-${threadId} [class^=card-body]`);

     setTimeout(() => {
        if (chatScreen.scrollHeight != chatScreen.scrollTop) {
            $(chatScreen).animate({scrollTop:chatScreen.scrollHeight}, 400);
            chatScreen.scrollTop = chatScreen.scrollHeight;
        }
     }, 400);

  }

  function sendMessage(threadId) {
      let textarea = document.querySelector('#tab-thread-content-' + threadId + ' [class~=chat-textarea]');
      let message = textarea.outerText;
      if(message != null && message != "" && message.match(/^[\n ]+$/) == null) {
          let json = JSON.parse('{"type":"message","object":{"id":1,"message":"","sender":"Admin","receiver":"Bitch","channelId":0,"date":""}}');
          json.object.message = message;
          json.object.channelId = threadId;
          clientWebSocket.send(JSON.stringify(json));
      }
      textarea.innerText = "";
  }

  function sendChannel(threadId, name) {
      clientWebSocket.send(`{"type":"channel","object":{"id":${threadId},"name":"${name}","unread":0}}`);
  }

  function filterTabs(filter) {
      if(filter) {
            currentFilter = filter;
            document.querySelectorAll('[role=tablist] a').forEach(el => el.classList.remove('active'));
            document.getElementById(filter + 'TabButton').classList.add('active');
      }
      if(currentFilter == 'all') {
            document.getElementById('tabs').innerHTML = allTabs.map(element => element.outerHTML).join("");
      } else {
            document.getElementById('tabs').innerHTML = allTabs
                .filter(element => element.querySelector('a').classList.contains(currentFilter))
                .map(element => element.outerHTML).join("");
      }
  }