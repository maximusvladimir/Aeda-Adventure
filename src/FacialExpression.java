public enum FacialExpression {
	MOUTH_SMILE, MOUTH_NEUTRAL, MOUTH_FROWN,

	EYEBROWS_NEUTRAL, EYEBROWS_ANGRY, EYEBROWS_INNOCENTLIKE,

	EMOTION_HAPPY, EMOTION_ANGRY, EMOTION_SAD, EMOTION_PLAIN, EMOTION_FOCUSED, EMOTION_REASSURING;

	public static FacialExpression getEmotion(int i) {
		switch (i % 6) {
		case 0:
			return FacialExpression.EMOTION_ANGRY;
		case 1:
			return FacialExpression.EMOTION_FOCUSED;
		case 2:
			return FacialExpression.EMOTION_HAPPY;
		case 3:
			return FacialExpression.EMOTION_PLAIN;
		case 4:
			return FacialExpression.EMOTION_SAD;
		case 5:
			return FacialExpression.EMOTION_REASSURING;
		default:
			return FacialExpression.EMOTION_PLAIN;
		}
	}

	public static FacialExpression getMouthExpression(int i) {
		switch (i % 3) {
		case 0:
			return FacialExpression.MOUTH_SMILE;
		case 1:
			return FacialExpression.MOUTH_NEUTRAL;
		case 2:
			return FacialExpression.MOUTH_FROWN;
		default:
			return FacialExpression.MOUTH_NEUTRAL;
		}
	}

	public static FacialExpression getEyeExpression(int i) {
		switch (i % 3) {
		case 0:
			return FacialExpression.EYEBROWS_INNOCENTLIKE;
		case 1:
			return FacialExpression.EYEBROWS_NEUTRAL;
		case 2:
			return FacialExpression.EYEBROWS_ANGRY;
		default:
			return FacialExpression.EYEBROWS_NEUTRAL;
		}
	}
}
