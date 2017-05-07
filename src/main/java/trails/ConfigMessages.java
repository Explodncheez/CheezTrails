package trails;

public class ConfigMessages {
	
	public enum ConfigMessage {
		ACTIVATE_EFFECT_SUCCESS("activate-effect-success"),
		ACTIVATE_EFFECT_FAIL("activate-effect-no-permission"),
		DEACTIVATE_EFFECT_SUCCESS("deactivate-effect-success"),
		DEACTIVATE_EFFECT_FAIL("deactivate-effect-no-active");
		
		private ConfigMessage(String path) {
			this.path = path;
		}
		
		private String path, s;
		
		public String getMessage(String trailName) {
			return s.replace("&", "§").replace("%TRAIL%", trailName);
		}
		
		public void loadMessage() {
			s = CheezTrails.config().getString("messages." + path);
		}
	}

}
