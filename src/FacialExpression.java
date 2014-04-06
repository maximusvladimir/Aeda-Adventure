public enum FacialExpression {
	MOUTH_SMILE, MOUTH_NEUTRAL, MOUTH_FROWN,

	EYEBROWS_NEUTRAL, EYEBROWS_ANGRY, EYEBROWS_INNOCENTLIKE;

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
