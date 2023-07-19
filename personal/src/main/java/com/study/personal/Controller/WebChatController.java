package com.study.personal.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/webchat/")
@ServerEndpoint("/webchat")
public class WebChatController extends Socket {
	
	private static final List<Session> session = new ArrayList<Session>();
	
	@PostMapping("/")
	public String webchat_main(@ModelAttribute("userid") String userid) {
		return "webchat";
	}
	
	@PostMapping("/exit")
	public String webchat_exit(@ModelAttribute("userid") String userid) {
		log.info(userid);
		return "main";
	}
	
	@OnOpen
	public void open(Session newUser) throws IOException {
		System.out.println("connected");
		System.out.println("채팅방 인원 : " + session.size());
		session.add(newUser);
	}
	
	@OnMessage
	public void getMsg(Session recieveSession, String msg) {
		for (int i = 0; i < session.size(); i++) {
			
			// 다른 사용자면 userid : msg
			if(!recieveSession.getId().equals(session.get(i).getId())) {
				try {
					session.get(i).getBasicRemote().sendText(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else { // 같은 사용자면 나 : msg
				try {
					String MyMsg = msg.substring(msg.indexOf(":") + 1);
					session.get(i).getBasicRemote().sendText(" 나 : " + MyMsg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	@OnClose
	public void close(Session User) throws IOException {
		log.info("@@@@@@@@@");
		session.remove(User);
	}
	
}
