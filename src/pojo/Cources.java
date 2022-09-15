package pojo;

import java.util.List;

public class Cources {

	private List<webAutomation> webAutomation;  //when there r list of array with indexes
	private List<Api> api;
	private List<Mobile> mobile;

	
	public List<webAutomation> getWebAutomation() {
		return webAutomation;
	}

	public void setWebAutomation(List<webAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}

	public List<Api> getApi() {
		return api;
	}

	public void setApi(List<Api> api) {
		this.api = api;
	}

	public List<Mobile> getMobile() {
		return mobile;
	}

	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}

}
