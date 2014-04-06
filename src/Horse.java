import java.awt.Color;

public class Horse extends Drawable {
	private PointTesselator tesselator;

	public Horse(Scene<Drawable> scene) {
		super(scene, new Hitbox());

		tesselator = new PointTesselator();
		tesselator.setDrawType(DrawType.Triangle);
		tesselator.setSkipCullCheck(true);
	}

	float rpw = 0.0f;

	public void draw(int darkness) {
		// tesselator.rotate(0, MathCalculator.PIOVER2, 0);
		tesselator.translate(pos.x, pos.y, pos.z, false);
		tesselator.rotate(0,rot,0);
		// Color horseColor = Utility.adjustBrightness(new Color(233,235,234),
		// -darkness);
		float t = (float)(Math.cos(tail) * 50);
		Color horseColor = Utility.adjustBrightness(new Color(100,68,30),
				-darkness);
		Color myColor = horseColor;
		float scale = 120;
		Rand var = new Rand(324);
		rpw += 0.1f;

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.288792f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.282489f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.217357f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.842511f * scale, -0.246837f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.211054f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.217357f * scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale,
				0.25314f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale, -0.246837f
				* scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.857863f * scale,
				0.25314f * scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale,
				0.25314f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.263852f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.270155f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.263852f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.270155f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.270155f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.244407f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.195776f * scale, 1.079255f * scale, -0.204314f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.195776f * scale, 1.079255f * scale,
				0.210616f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale, -0.204314f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale,
				0.252106f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale, -0.245804f
				* scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale,
				0.252106f * scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale,
				0.237557f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale, -0.231254f
				* scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale,
				0.237557f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.02309f * scale, 0.011129f * scale, -0.212785f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.236276f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);
		tesselator.point(-1.941676f * scale, 0.921024f * scale,
				0.080738f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.941676f * scale, 0.921024f * scale, -0.074435f
				* scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.941676f * scale, 0.921024f * scale,
				0.080738f * scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);
		tesselator.point(-2.233984f * scale, 0.977481f * scale,
				0.123959f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale, -0.117656f
				* scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale,
				0.123959f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.985672f * scale, 2.119846f * scale, -0.048502f
				* scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-2.027788f * scale, 2.766639f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);
		tesselator.point(-1.682449f * scale, 3.15999f * scale,
				0.105807f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale, -0.250782f
				* scale);
		tesselator.point(-0.356882f * scale, 3.547596f * scale,
				0.003151f * scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);
		tesselator.point(-0.356882f * scale, 3.547596f * scale,
				0.003151f * scale);
		tesselator.point(-0.354585f * scale, 3.547596f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale, -0.250782f
				* scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);
		tesselator.point(-0.356882f * scale, 3.547596f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale,
				0.257085f * scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);
		tesselator.point(0.543849f * scale, 3.598827f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale, -0.250782f
				* scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale,
				0.257085f * scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.979098f * scale, 3.164123f * scale, -0.125553f
				* scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);
		tesselator.point(3.004136f * scale, 3.124711f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);
		tesselator.point(2.84342f * scale, 3.152131f * scale, 0.15673f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator.point(3.004136f * scale, 3.124711f * scale,
				0.003151f * scale);
		tesselator
				.point(3.076676f * scale, 3.28103f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(3.076676f * scale, 3.28103f * scale, 0.003151f * scale);
		tesselator.point(3.004136f * scale, 3.124711f * scale,
				0.003151f * scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.212785f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.227527f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.496652f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, -0.49893f * scale);
		tesselator.point(1.02309f * scale, 0.011129f * scale, -0.212785f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.212785f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.227527f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.236276f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.487902f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);
		tesselator.point(1.02309f * scale, 0.011129f * scale, -0.212785f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.236276f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale, -0.473681f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale,
				0.479984f * scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale, -0.231254f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale,
				0.237557f * scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);
		tesselator
				.point(1.567291f * scale, 3.92278f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-2.027788f * scale, 2.766639f * scale,
				0.054805f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);
		tesselator.point(1.216537f * scale, 3.653627f * scale,
				0.003151f * scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);
		tesselator.point(1.216537f * scale, 3.653627f * scale,
				0.003151f * scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale, -0.165927f
				* scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);
		tesselator.point(1.844908f * scale, 3.061499f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale,
				0.172229f * scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale,
				0.509847f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale, -0.503545f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale,
				0.509847f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale,
				0.509847f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale, -0.401076f
				* scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(1.092986f * scale, 0.327549f * scale,
				0.479984f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale, -0.245804f
				* scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale, -0.231254f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.925105f * scale, 0.412571f * scale,
				0.237557f * scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale,
				0.252106f * scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale, -0.204314f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);
		tesselator.point(1.023853f * scale, 0.785768f * scale,
				0.407379f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale, -0.245804f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale, -0.204314f
				* scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale,
				0.210616f * scale);
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.463579f
				* scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.249771f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.481839f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.463579f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.249771f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.481839f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.263852f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.270155f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.270155f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.263852f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.473105f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.473105f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale, -0.246837f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale,
				0.25314f * scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale, -0.246837f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale,
				0.25314f * scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.211054f
				* scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.217357f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.508887f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.282489f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.288792f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.502584f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.211054f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.508887f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.51104f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);
		tesselator.point(2.172622f * scale, 3.517898f * scale,
				0.003151f * scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);
		tesselator.point(2.172622f * scale, 3.517898f * scale,
				0.003151f * scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);
		tesselator
				.point(1.567291f * scale, 3.92278f * scale, 0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.567291f * scale, 3.92278f * scale, 0.003151f * scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(1.844908f * scale, 3.061499f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(1.844908f * scale, 3.061499f * scale,
				0.003151f * scale);
		tesselator.point(1.797913f * scale, 3.070461f * scale,
				0.172229f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);
		tesselator
				.point(1.00366f * scale, 3.385968f * scale, 0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator.point(0.181783f * scale, 1.844439f * scale,
				0.003151f * scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator.point(0.181783f * scale, 1.844439f * scale,
				0.003151f * scale);
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);
		tesselator.point(0.181783f * scale, 1.844439f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale,
				0.558246f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.049724f * scale, 3.362382f * scale, -0.250782f
				* scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.049724f * scale, 3.362382f * scale,
				0.257085f * scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.515844f * scale, 2.194881f * scale, -0.551943f
				* scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.515844f * scale, 2.194881f * scale,
				0.558246f * scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale, -0.099505f
				* scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale,
				0.105807f * scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.504738f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.504738f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.51104f * scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.502584f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.502584f
				* scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.508887f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.466802f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.473105f * scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.466802f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.466802f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.473105f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.473105f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.491944f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.481839f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale, -0.401076f
				* scale);
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale,
				0.407379f * scale);
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);
		tesselator.point(1.023853f * scale, 0.785768f * scale,
				0.407379f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale, -0.503545f
				* scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale, -0.503545f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.316777f * scale, 1.828274f * scale,
				0.509847f * scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale,
				0.509847f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.841751f * scale, 1.667115f * scale, -0.503545f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale,
				0.509847f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale, -0.473681f
				* scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);
		tesselator.point(1.092986f * scale, 0.327549f * scale,
				0.479984f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, -0.49893f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.487902f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);
		tesselator.point(-1.682449f * scale, 3.15999f * scale, -0.099505f
				* scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale,
				0.105807f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale, -0.551943f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale, -0.767058f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale, -0.551943f
				* scale);
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale,
				0.558246f * scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale, -0.767058f
				* scale);
		tesselator.point(0.816341f * scale, 2.663285f * scale, -0.670583f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.816341f * scale, 2.663286f * scale,
				0.676886f * scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale,
				0.773361f * scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.295685f * scale, 2.613616f * scale, -0.767058f
				* scale);
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale,
				0.773361f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(0.816341f * scale, 2.663285f * scale, -0.670583f
				* scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator.point(0.816341f * scale, 2.663286f * scale,
				0.676886f * scale);
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);
		tesselator.point(0.816341f * scale, 2.663286f * scale,
				0.676886f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(1.797913f * scale, 3.070461f * scale, -0.165927f
				* scale);
		tesselator.point(1.844908f * scale, 3.061499f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale, -0.238532f
				* scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.500751f * scale, 4.045944f * scale, -0.243297f
				* scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale, -0.276007f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.500751f * scale, 4.045944f * scale,
				0.249599f * scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale,
				0.282309f * scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(1.023853f * scale, 0.785768f * scale, -0.401076f
				* scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);
		tesselator.point(1.00366f * scale, 3.385968f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.00366f * scale, 3.385968f * scale, 0.257085f * scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale, -0.165927f
				* scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(1.797913f * scale, 3.070461f * scale,
				0.172229f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(0.543849f * scale, 3.598827f * scale, -0.250782f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale,
				0.257085f * scale);
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale, -0.243297f
				* scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);
		tesselator.point(-2.027788f * scale, 2.766639f * scale, -0.048502f
				* scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.985672f * scale, 2.119846f * scale, -0.048502f
				* scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale, -0.048502f
				* scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale,
				0.054805f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);
		tesselator.point(-2.233984f * scale, 0.977481f * scale, -0.117656f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale,
				0.123959f * scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale, -0.243297f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.492174f * scale, 4.131122f * scale,
				0.084069f * scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale,
				0.084069f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale,
				0.084069f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale, -0.077767f
				* scale);
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale,
				0.084069f * scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale, -0.077767f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.487279f * scale, 4.172295f * scale,
				0.283448f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale, -0.277146f
				* scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale,
				0.283448f * scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);
		tesselator
				.point(2.479985f * scale, 4.345538f * scale, 0.34503f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(1.216537f * scale, 3.653627f * scale,
				0.003151f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);
		tesselator.point(2.478756f * scale, 4.359181f * scale, -0.304337f
				* scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale, -0.277146f
				* scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);
		tesselator.point(2.478756f * scale, 4.359181f * scale, -0.304337f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.478756f * scale, 4.359181f * scale,
				0.310639f * scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);
		tesselator.point(2.487279f * scale, 4.172295f * scale,
				0.283448f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator
				.point(3.076676f * scale, 3.28103f * scale, 0.003151f * scale);
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);
		tesselator
				.point(3.076676f * scale, 3.28103f * scale, 0.003151f * scale);
		tesselator.point(3.064589f * scale, 3.300944f * scale,
				0.113879f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, -0.150428f
				* scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, 0.15673f * scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale,
				0.200937f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale, -0.125553f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(3.064589f * scale, 3.300944f * scale,
				0.113879f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);
		tesselator
				.point(2.85074f * scale, 3.612776f * scale, 0.071897f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);
		tesselator.point(2.84342f * scale, 3.152131f * scale, -0.150428f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, 0.15673f * scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale, -0.194635f
				* scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale,
				0.200937f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.492174f * scale, 4.131122f * scale, -0.077767f
				* scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);
		tesselator.point(2.486926f * scale, 3.372146f * scale,
				0.003151f * scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);
		tesselator.point(2.486926f * scale, 3.372146f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);
		tesselator.point(2.486926f * scale, 3.372146f * scale,
				0.003151f * scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);
		tesselator.point(2.486926f * scale, 3.372146f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.85074f * scale, 3.612776f * scale, -0.065595f
				* scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale, -0.276007f
				* scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale,
				0.282309f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(2.85074f * scale, 3.612776f * scale, 0.071897f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale, -0.194635f
				* scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale, -0.238532f
				* scale);
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale,
				0.244835f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale,
				0.200937f * scale);
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.211054f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.288792f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.842511f * scale, -0.246837f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale,
				0.25314f * scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale,
				0.25314f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale, -0.246837f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.857863f * scale,
				0.25314f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.263852f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.270155f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.270155f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.249771f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.270155f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.244407f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.195776f * scale, 1.079255f * scale, -0.204314f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.195776f * scale, 1.079255f * scale,
				0.210616f * scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale, -0.245804f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale,
				0.210616f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale, -0.231254f
				* scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale, -0.245804f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale,
				0.252106f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.02309f * scale, 0.011129f * scale, -0.212785f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.212785f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.236276f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.227527f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-1.941676f * scale, 0.921024f * scale, -0.074435f
				* scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);
		tesselator.point(-1.879095f * scale, 1.348548f * scale,
				0.003151f * scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.941676f * scale, 0.921024f * scale,
				0.080738f * scale);
		tesselator.point(-1.894268f * scale, 0.921024f * scale,
				0.003151f * scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale, -0.117656f
				* scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale,
				0.123959f * scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale, -0.048502f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.94312f * scale, 2.118548f * scale,
				0.003151f * scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale,
				0.054805f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale, -0.048502f
				* scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);
		tesselator.point(-2.212862f * scale, 1.37175f * scale,
				0.003151f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.133434f * scale, 2.124119f * scale, -0.048502f
				* scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.133434f * scale, 2.124119f * scale,
				0.054805f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale,
				0.054805f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale, -0.048502f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);
		tesselator.point(2.172622f * scale, 3.517898f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.027788f * scale, 2.766639f * scale, -0.048502f
				* scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.027788f * scale, 2.766639f * scale,
				0.054805f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);
		tesselator.point(-2.046821f * scale, 2.797508f * scale,
				0.003151f * scale);
		tesselator.point(-2.027788f * scale, 2.766639f * scale, -0.048502f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.133434f * scale, 2.124119f * scale, -0.048502f
				* scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale, -0.048502f
				* scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale, -0.250782f
				* scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);
		tesselator.point(-0.354585f * scale, 3.547596f * scale,
				0.257085f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale, -0.250782f
				* scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.354585f * scale, 3.547596f * scale,
				0.257085f * scale);
		tesselator.point(-0.356882f * scale, 3.547596f * scale,
				0.003151f * scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);
		tesselator.point(0.543849f * scale, 3.598827f * scale, -0.250782f
				* scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);
		tesselator.point(0.120569f * scale, 3.402264f * scale,
				0.003151f * scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale, -0.250782f
				* scale);
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale,
				0.257085f * scale);
		tesselator.point(0.541552f * scale, 3.598827f * scale,
				0.003151f * scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.979098f * scale, 3.164123f * scale, -0.125553f
				* scale);
		tesselator.point(2.84342f * scale, 3.152131f * scale, -0.150428f
				* scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);
		tesselator.point(3.004136f * scale, 3.124711f * scale,
				0.003151f * scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(3.076676f * scale, 3.28103f * scale, 0.003151f * scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);
		tesselator.point(3.064589f * scale, 3.300944f * scale,
				0.113879f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.659356f * scale, 2.023405f * scale,
				0.003151f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.49893f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.212785f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.227527f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, -0.49893f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.212785f
				* scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.49893f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.227527f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.487902f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.496652f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);
		tesselator.point(1.02309f * scale, 0.011129f * scale, -0.212785f
				* scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, -0.49893f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.236276f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.487902f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale, -0.473681f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.263094f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale,
				0.479984f * scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale,
				0.269397f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.263094f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.269397f * scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);
		tesselator.point(1.216537f * scale, 3.653627f * scale,
				0.003151f * scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.216537f * scale, 3.653627f * scale,
				0.003151f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);
		tesselator.point(1.00366f * scale, 3.385968f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);
		tesselator
				.point(1.00366f * scale, 3.385968f * scale, 0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale, -0.165927f
				* scale);
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale,
				0.172229f * scale);
		tesselator.point(1.844908f * scale, 3.061499f * scale,
				0.003151f * scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);
		tesselator
				.point(1.58928f * scale, 2.499142f * scale, 0.003151f * scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale, -0.503545f
				* scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);
		tesselator.point(1.530085f * scale, 2.016177f * scale,
				0.003151f * scale);
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);
		tesselator.point(1.366069f * scale, 1.769523f * scale, -0.190488f
				* scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale, -0.503545f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.366069f * scale, 1.769523f * scale, 0.19679f * scale);
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale,
				0.509847f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.382967f * scale, 3.434981f * scale,
				0.244835f * scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale, -0.503545f
				* scale);
		tesselator.point(0.841751f * scale, 1.626662f * scale, -0.224717f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale, -0.077767f
				* scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale,
				0.407379f * scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);
		tesselator.point(0.925105f * scale, 0.412571f * scale, -0.231254f
				* scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.925105f * scale, 0.412571f * scale,
				0.237557f * scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(1.023853f * scale, 0.785768f * scale, -0.401076f
				* scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale, -0.245804f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);
		tesselator.point(1.195776f * scale, 1.079255f * scale,
				0.210616f * scale);
		tesselator.point(1.073145f * scale, 0.783029f * scale,
				0.252106f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale, -0.414554f
				* scale);
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);
		tesselator.point(0.92097f * scale, 1.071368f * scale, -0.204314f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);
		tesselator.point(0.929025f * scale, 0.775143f * scale,
				0.252106f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(1.182361f * scale, 1.312491f * scale, -0.262866f
				* scale);
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.182361f * scale, 1.312491f * scale,
				0.269168f * scale);
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.262866f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);
		tesselator
				.point(0.92097f * scale, 1.071368f * scale, 0.210616f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.269168f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.463579f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.263852f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.270155f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.244407f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.463579f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.491944f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.481839f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.481839f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.221695f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.491944f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.244407f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.481839f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.466802f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.473105f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.466802f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.270155f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.466802f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.246837f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);
		tesselator.point(-1.344231f * scale, 0.857863f * scale,
				0.25314f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.25314f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.246837f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.466802f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.25314f * scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.473105f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.211054f
				* scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.217357f * scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.502584f
				* scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.211054f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.522875f * scale, 0.842511f * scale,
				0.25314f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.217357f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.282489f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.288792f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.631229f * scale, 1.241338f * scale, -0.502584f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.508887f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.217357f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.282489f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.285283f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.504738f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.291586f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale,
				0.105807f * scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale,
				0.257085f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-1.146724f * scale, 1.804532f * scale, -0.250804f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.250804f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.504738f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.257107f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.51104f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale, -0.238532f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale,
				0.244835f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);
		tesselator
				.point(1.567291f * scale, 3.92278f * scale, 0.003151f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.567291f * scale, 3.92278f * scale, 0.003151f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(2.172622f * scale, 3.517898f * scale,
				0.003151f * scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(2.172622f * scale, 3.517898f * scale,
				0.003151f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(1.00366f * scale, 3.385968f * scale, -0.250782f
				* scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);
		tesselator.point(0.754024f * scale, 3.422754f * scale,
				0.003151f * scale);
		tesselator.point(0.979854f * scale, 3.472007f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale, -0.551943f
				* scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator.point(0.181783f * scale, 1.844439f * scale,
				0.003151f * scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.049724f * scale, 3.362382f * scale, -0.250782f
				* scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.049724f * scale, 3.362382f * scale,
				0.257085f * scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);
		tesselator.point(-0.453383f * scale, 3.324966f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.515844f * scale, 2.194881f * scale, -0.551943f
				* scale);
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.515844f * scale, 2.194881f * scale,
				0.558246f * scale);
		tesselator
				.point(-0.51814f * scale, 2.03666f * scale, 0.003151f * scale);
		tesselator.point(-0.859176f * scale, 2.171311f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale, -0.099505f
				* scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale, -0.250782f
				* scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.682449f * scale, 3.15999f * scale,
				0.105807f * scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);
		tesselator.point(-1.073531f * scale, 3.44842f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.51104f * scale);
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale, -0.504738f
				* scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale, -0.504738f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.273019f * scale, 1.33439f * scale,
				0.443653f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);
		tesselator.point(-1.530197f * scale, 1.676854f * scale,
				0.51104f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.273019f * scale, 1.33439f * scale, -0.43735f
				* scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale, -0.464058f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);
		tesselator.point(-1.631229f * scale, 1.241338f * scale,
				0.508887f * scale);
		tesselator.point(-1.534167f * scale, 1.401541f * scale,
				0.470361f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-1.304078f * scale, 1.149435f * scale, -0.502585f
				* scale);
		tesselator.point(-1.522875f * scale, 0.851847f * scale, -0.415587f
				* scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.522875f * scale, 0.851847f * scale,
				0.42189f * scale);
		tesselator.point(-1.304078f * scale, 1.149435f * scale,
				0.508887f * scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.344231f * scale, 0.873422f * scale, -0.412892f
				* scale);
		tesselator.point(-1.382244f * scale, 0.298115f * scale, -0.466802f
				* scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.466802f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.382244f * scale, 0.298115f * scale,
				0.473105f * scale);
		tesselator.point(-1.344231f * scale, 0.873422f * scale,
				0.419195f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.473105f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.182974f * scale, 0.375345f * scale, -0.466802f
				* scale);
		tesselator.point(-1.214814f * scale, 0.134756f * scale, -0.449786f
				* scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.214814f * scale, 0.134756f * scale,
				0.456089f * scale);
		tesselator.point(-1.182974f * scale, 0.375345f * scale,
				0.473105f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.110424f * scale, 0.187235f * scale, -0.449786f
				* scale);
		tesselator.point(-1.249158f * scale, 0.001219f * scale, -0.491944f
				* scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale, -0.463579f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.249158f * scale, 0.001219f * scale,
				0.481839f * scale);
		tesselator.point(-1.110424f * scale, 0.187235f * scale,
				0.456089f * scale);
		tesselator.point(-0.934383f * scale, 0.001219f * scale,
				0.481839f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale, -0.448706f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.146484f * scale, 1.072658f * scale,
				0.481215f * scale);
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);
		tesselator.point(0.931316f * scale, 1.239261f * scale,
				0.455009f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale, -0.401076f
				* scale);
		tesselator.point(1.146484f * scale, 1.072658f * scale, -0.474912f
				* scale);
		tesselator.point(0.92097f * scale, 1.055809f * scale, -0.474912f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.023853f * scale, 0.785768f * scale,
				0.407379f * scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);
		tesselator
				.point(0.92097f * scale, 1.055809f * scale, 0.481215f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.790757f * scale, 2.844083f * scale, -0.107907f
				* scale);
		tesselator.point(-1.682449f * scale, 3.15999f * scale, -0.099505f
				* scale);
		tesselator.point(-1.706255f * scale, 3.246029f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.925105f * scale, 0.319662f * scale, -0.473681f
				* scale);
		tesselator.point(1.092986f * scale, 0.327549f * scale, -0.473681f
				* scale);
		tesselator.point(1.092986f * scale, 0.420458f * scale, -0.231254f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale, -0.503545f
				* scale);
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.316777f * scale, 1.828274f * scale,
				0.509847f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.841751f * scale, 1.667115f * scale, -0.503545f
				* scale);
		tesselator.point(1.13307f * scale, 1.321453f * scale, -0.448706f
				* scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale, -0.503545f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.13307f * scale, 1.321453f * scale, 0.455009f * scale);
		tesselator.point(0.841751f * scale, 1.667115f * scale,
				0.509847f * scale);
		tesselator.point(1.316777f * scale, 1.828274f * scale,
				0.509847f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.327549f * scale, -0.473681f
				* scale);
		tesselator.point(1.047472f * scale, 0.18059f * scale, -0.448478f
				* scale);
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(1.047472f * scale, 0.18059f * scale, 0.454781f * scale);
		tesselator.point(1.092986f * scale, 0.327549f * scale,
				0.479984f * scale);
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.173772f * scale, 0.212238f * scale, -0.448478f
				* scale);
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, -0.49893f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale, -0.49893f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.02309f * scale, 0.011129f * scale, 0.487902f * scale);
		tesselator
				.point(1.173772f * scale, 0.212238f * scale, 0.45478f * scale);
		tesselator.point(1.363239f * scale, 0.001219f * scale,
				0.496652f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale, -0.437068f
				* scale);
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.69812f * scale, 2.023405f * scale,
				0.443371f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(0.841751f * scale, 1.626662f * scale, 0.23102f * scale);
		tesselator.point(0.797878f * scale, 1.865724f * scale,
				0.003151f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale, -0.250782f
				* scale);
		tesselator.point(-1.682449f * scale, 3.15999f * scale, -0.099505f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(-1.049724f * scale, 3.362382f * scale, -0.250782f
				* scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale,
				0.623981f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);
		tesselator.point(-0.923494f * scale, 2.799285f * scale, -0.617678f
				* scale);
		tesselator.point(-0.856879f * scale, 2.3901f * scale, -0.637278f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);
		tesselator.point(-0.515844f * scale, 2.194881f * scale,
				0.558246f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale,
				0.773361f * scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.449605f * scale, 2.672348f * scale, -0.567885f
				* scale);
		tesselator.point(0.18408f * scale, 2.002659f * scale, -0.551944f
				* scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale, -0.767058f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator
				.point(0.18408f * scale, 2.002659f * scale, 0.558246f * scale);
		tesselator.point(-0.449605f * scale, 2.672348f * scale,
				0.574187f * scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale,
				0.773361f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(0.816341f * scale, 2.663285f * scale, -0.670583f
				* scale);
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.816341f * scale, 2.663286f * scale,
				0.676886f * scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.295685f * scale, 2.613616f * scale, -0.767058f
				* scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);
		tesselator.point(0.816341f * scale, 2.663285f * scale, -0.670583f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);
		tesselator.point(0.295685f * scale, 2.613616f * scale,
				0.773361f * scale);
		tesselator.point(0.816341f * scale, 2.663286f * scale,
				0.676886f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.77783f * scale, 3.336716f * scale, -0.250782f
				* scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);
		tesselator.point(1.00366f * scale, 3.385968f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator
				.point(0.77783f * scale, 3.336716f * scale, 0.257085f * scale);
		tesselator
				.point(1.00366f * scale, 3.385968f * scale, 0.257085f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);
		tesselator.point(0.816341f * scale, 2.663285f * scale, -0.670583f
				* scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale, -0.551944f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);
		tesselator.point(0.847597f * scale, 2.079771f * scale,
				0.558246f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.591097f * scale, 3.836741f * scale, -0.061467f
				* scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.153309f * scale, 3.568321f * scale,
				0.167861f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(1.591097f * scale, 3.836741f * scale,
				0.067769f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.500751f * scale, 4.045944f * scale, -0.243297f
				* scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.500751f * scale, 4.045944f * scale,
				0.249599f * scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale,
				0.282309f * scale);
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale, -0.194635f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(2.153309f * scale, 3.568321f * scale, -0.161559f
				* scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale, -0.238532f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);
		tesselator.point(1.483091f * scale, 2.127829f * scale, -0.471008f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);
		tesselator
				.point(1.483091f * scale, 2.127829f * scale, 0.47731f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);
		tesselator.point(1.00366f * scale, 3.385968f * scale, -0.250782f
				* scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale, -0.521203f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(1.00366f * scale, 3.385968f * scale, 0.257085f * scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);
		tesselator.point(1.257266f * scale, 2.744092f * scale,
				0.527506f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.797913f * scale, 3.070461f * scale, -0.165927f
				* scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);
		tesselator.point(1.542285f * scale, 2.508104f * scale, -0.162608f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);
		tesselator.point(1.797913f * scale, 3.070461f * scale,
				0.172229f * scale);
		tesselator
				.point(1.542285f * scale, 2.508104f * scale, 0.16891f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.708396f * scale, 3.492139f * scale, -0.202234f
				* scale);
		tesselator.point(1.240343f * scale, 3.567588f * scale, -0.250782f
				* scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(1.240343f * scale, 3.567588f * scale,
				0.257085f * scale);
		tesselator.point(1.708396f * scale, 3.492139f * scale,
				0.208536f * scale);
		tesselator.point(1.405205f * scale, 3.076384f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(0.217426f * scale, 3.167543f * scale, -0.361024f
				* scale);
		tesselator.point(0.543849f * scale, 3.598827f * scale, -0.250782f
				* scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.543849f * scale, 3.598827f * scale,
				0.257085f * scale);
		tesselator.point(0.217426f * scale, 3.167543f * scale,
				0.367327f * scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-0.429577f * scale, 3.238927f * scale, -0.250782f
				* scale);
		tesselator.point(0.122866f * scale, 3.402264f * scale, -0.250782f
				* scale);
		tesselator.point(-0.354585f * scale, 3.547596f * scale, -0.250782f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(0.122866f * scale, 3.402264f * scale,
				0.257085f * scale);
		tesselator.point(-0.429577f * scale, 3.238927f * scale,
				0.257085f * scale);
		tesselator.point(-0.354585f * scale, 3.547596f * scale,
				0.257085f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(-2.027788f * scale, 2.766639f * scale,
				0.054805f * scale);
		tesselator.point(-1.790757f * scale, 2.844083f * scale,
				0.114209f * scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);
		tesselator.point(-2.133434f * scale, 2.124119f * scale,
				0.054805f * scale);
		tesselator.point(-2.180581f * scale, 2.125416f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.133434f * scale, 2.124119f * scale,
				0.054805f * scale);
		tesselator.point(-1.95024f * scale, 2.623603f * scale,
				0.054805f * scale);
		tesselator.point(-1.935801f * scale, 2.592734f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(1.092986f * scale, 0.420458f * scale,
				0.237557f * scale);
		tesselator.point(0.925105f * scale, 0.319662f * scale,
				0.479984f * scale);
		tesselator.point(0.929025f * scale, 0.784478f * scale,
				0.420856f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.985672f * scale, 2.119846f * scale, -0.048502f
				* scale);
		tesselator.point(-2.184656f * scale, 1.369949f * scale, -0.048502f
				* scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.184656f * scale, 1.369949f * scale,
				0.054805f * scale);
		tesselator.point(-1.985672f * scale, 2.119846f * scale,
				0.054805f * scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.902708f * scale, 1.350349f * scale, -0.048502f
				* scale);
		tesselator.point(-2.233984f * scale, 0.977481f * scale, -0.117656f
				* scale);
		tesselator.point(-1.941676f * scale, 0.921024f * scale, -0.074435f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.233984f * scale, 0.977481f * scale,
				0.123959f * scale);
		tesselator.point(-1.902708f * scale, 1.350349f * scale,
				0.054805f * scale);
		tesselator.point(-1.941676f * scale, 0.921024f * scale,
				0.080738f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale, -0.178946f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale,
				0.249599f * scale);
		tesselator.point(2.340091f * scale, 4.102923f * scale,
				0.185248f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-2.027788f * scale, 2.766639f * scale, -0.048502f
				* scale);
		tesselator.point(-1.95024f * scale, 2.623603f * scale, -0.048502f
				* scale);
		tesselator.point(-1.821147f * scale, 2.844083f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale, -0.125553f
				* scale);
		tesselator.point(3.004136f * scale, 3.124711f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale, -0.077767f
				* scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.363146f * scale, 4.194501f * scale,
				0.087923f * scale);
		tesselator.point(2.332009f * scale, 4.201557f * scale,
				0.003151f * scale);
		tesselator.point(2.504678f * scale, 4.140579f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);
		tesselator.point(2.363146f * scale, 4.194501f * scale, -0.081621f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);
		tesselator.point(2.478756f * scale, 4.359181f * scale,
				0.310639f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.494115f * scale, 4.181523f * scale, -0.129569f
				* scale);
		tesselator.point(2.487279f * scale, 4.172295f * scale, -0.277146f
				* scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.494115f * scale, 4.181523f * scale,
				0.135871f * scale);
		tesselator.point(2.492174f * scale, 4.131122f * scale,
				0.084069f * scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale, -0.277146f
				* scale);
		tesselator.point(2.479985f * scale, 4.345538f * scale, -0.338727f
				* scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale,
				0.283448f * scale);
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.478756f * scale, 4.359181f * scale, -0.304337f
				* scale);
		tesselator.point(2.366349f * scale, 4.131365f * scale, -0.187955f
				* scale);
		tesselator.point(2.479985f * scale, 4.345538f * scale, -0.338727f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.366349f * scale, 4.131365f * scale,
				0.194257f * scale);
		tesselator.point(2.478756f * scale, 4.359181f * scale,
				0.310639f * scale);
		tesselator
				.point(2.479985f * scale, 4.345538f * scale, 0.34503f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.487279f * scale, 4.172295f * scale, -0.277146f
				* scale);
		tesselator.point(2.478756f * scale, 4.359181f * scale, -0.304337f
				* scale);
		tesselator.point(2.479985f * scale, 4.345538f * scale, -0.338727f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.478756f * scale, 4.359181f * scale,
				0.310639f * scale);
		tesselator.point(2.487279f * scale, 4.172295f * scale,
				0.283448f * scale);
		tesselator
				.point(2.479985f * scale, 4.345538f * scale, 0.34503f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);
		tesselator.point(2.85074f * scale, 3.612776f * scale, -0.065595f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);
		tesselator
				.point(2.85074f * scale, 3.612776f * scale, 0.071897f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.973321f * scale, 3.448696f * scale,
				0.003151f * scale);
		tesselator.point(3.064589f * scale, 3.300944f * scale,
				0.113879f * scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, -0.150428f
				* scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, 0.15673f * scale);
		tesselator.point(2.853738f * scale, 3.110709f * scale,
				0.003151f * scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale, -0.194635f
				* scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);
		tesselator.point(2.733692f * scale, 3.227744f * scale,
				0.003151f * scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(3.061184f * scale, 3.280519f * scale, -0.107577f
				* scale);
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(3.064589f * scale, 3.300944f * scale,
				0.113879f * scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale, -0.086952f
				* scale);
		tesselator.point(2.85074f * scale, 3.612776f * scale, -0.065595f
				* scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.962812f * scale, 3.455459f * scale,
				0.093254f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);
		tesselator.point(2.84342f * scale, 3.152131f * scale, -0.150428f
				* scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale, -0.125553f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.84342f * scale, 3.152131f * scale, 0.15673f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);
		tesselator.point(2.979098f * scale, 3.164123f * scale,
				0.131855f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.783066f * scale, 3.497146f * scale, -0.194435f
				* scale);
		tesselator.point(2.749705f * scale, 3.268279f * scale, -0.172146f
				* scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale, -0.159394f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.749705f * scale, 3.268279f * scale,
				0.178448f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);
		tesselator.point(2.882789f * scale, 3.327733f * scale,
				0.165696f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale, -0.219815f
				* scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.687252f * scale, 3.892018f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.85074f * scale, 3.612776f * scale, -0.065595f
				* scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);
		tesselator.point(2.859614f * scale, 3.619629f * scale,
				0.003151f * scale);
		tesselator
				.point(2.85074f * scale, 3.612776f * scale, 0.071897f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.488729f * scale, 4.067423f * scale,
				0.226117f * scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale,
				0.249599f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale, -0.194635f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale, -0.216583f
				* scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale, -0.238532f
				* scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);
		tesselator.point(2.364463f * scale, 3.395362f * scale,
				0.003151f * scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale,
				0.244835f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.517817f * scale, 3.411764f * scale,
				0.222886f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale,
				0.200937f * scale);
		tesselator.point(2.609389f * scale, 3.348929f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.85074f * scale, 3.612776f * scale, -0.065595f
				* scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale, -0.121298f
				* scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale, -0.243297f
				* scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale, -0.276007f
				* scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale,
				0.282309f * scale);
		tesselator.point(2.500751f * scale, 4.045944f * scale,
				0.249599f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator
				.point(2.85074f * scale, 3.612776f * scale, 0.071897f * scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.680852f * scale, 3.841274f * scale,
				0.127601f * scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.783066f * scale, 3.497146f * scale,
				0.200737f * scale);
		tesselator.point(2.652666f * scale, 3.388547f * scale,
				0.200937f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale, -0.255042f
				* scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale, -0.235221f
				* scale);
		tesselator.point(2.436867f * scale, 3.878502f * scale, -0.276007f
				* scale);

		tesselator.color(var.variate(myColor, 20));
		tesselator.point(2.347993f * scale, 3.871162f * scale,
				0.261344f * scale);
		tesselator.point(2.609967f * scale, 3.687824f * scale,
				0.241523f * scale);
		tesselator.point(2.382967f * scale, 3.434981f * scale,
				0.244835f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.941676f * scale, 0.921024f * scale, -0.074435f
				* scale);
		tesselator.point(-2.233984f * scale, 0.977481f * scale, -0.117656f
				* scale);
		tesselator.point(-2.314081f * scale, 0.977481f * scale,
				0.003151f * scale);

		tesselator.color(var.bright(myColor, 20));
		tesselator.point(-1.146724f * scale, 1.804532f * scale,
				0.257107f * scale);
		tesselator.point(-1.189404f * scale, 1.771336f * scale,
				0.51104f * scale);
		tesselator
				.point(-0.856879f * scale, 2.3901f * scale, 0.643581f * scale);

		// generateSide(rand,horseColor,-60,rpw);
		// generateSide(rand,horseColor,60,rpw);
	}

	public void generateSide(Rand rand, Color horseColor, float z, float rpw) {
		float speed = 1.1f;
		float zoff = 0.0f;
		if (z < 0)
			zoff = (float) (Math.sin(rpw * speed) * 40 * speed);
		else
			zoff = (float) (Math.cos(rpw * speed) * 40 * speed);
		float zof3 = (-zoff / 1.8f) + 20;
		float zof2 = (-zoff / 2.75f) + 20;
		float lift = (float) (Math.sin(rpw * speed) * 40);
		zoff += 20;

		// midbody
		tesselator.color(rand.bright(horseColor, 25));
		tesselator.point(-200, 100, z);
		tesselator.point(160, 100, z);
		tesselator.point(140, -35, z);

		tesselator.color(rand.bright(horseColor, 25));
		tesselator.point(140, -35, z);
		tesselator.point(-200, 100, z);
		tesselator.point(-185, -35, z);

		tesselator.color(Utility.adjustBrightness(rand.bright(horseColor, 25),
				10));
		tesselator.point(-200, 100, z);
		tesselator.point(-315, -35, z);
		tesselator.point(-185, -35, z);

		// backpart of front leg
		tesselator.color(rand.bright(horseColor, 25));
		tesselator.point(-315, -35, z);
		tesselator.point(-185, -35, z);
		tesselator.point(-220 + zof2, -100, z);

		tesselator.color(rand.bright(horseColor, -35, -20));
		tesselator.point(-315, -35, z);
		tesselator.point(-300 + zof2, -100, z);
		tesselator.point(-220 + zof2, -100, z);

		tesselator.color(rand.bright(horseColor, -35, -20));
		tesselator.point(-300 + zof2, -100, z);
		tesselator.point(-220 + zof2, -100, z);
		tesselator.point(-250 + zof3, -150, z);

		tesselator.color(Utility.adjustBrightness(horseColor, -40));
		tesselator.point(-300 + zof2, -100, z);
		tesselator.point(-250 + zof3, -150, z);
		tesselator.point(-260 + zoff, -225 + lift, z);

		tesselator.color(Utility.adjustBrightness(horseColor, -45));
		tesselator.point(-260 + zoff, -225 + lift, z);
		tesselator.point(-300 + zof2, -100, z);
		tesselator.point(-300 + zoff, -225 + lift, z);

		// front leg inner
		float fana = 35;
		if (z > 0)
			fana = -fana;
		tesselator.color(rand.bright(horseColor, -20, -10));
		tesselator.point(-315, -35, z + fana);
		tesselator.point(-185, -35, z + fana);
		tesselator.point(-220 + zof2, -100, z + fana);

		tesselator.color(rand.bright(horseColor, -45, -35));
		tesselator.point(-315, -35, z + fana);
		tesselator.point(-300 + zof2, -100, z + fana);
		tesselator.point(-220 + zof2, -100, z + fana);

		tesselator.color(rand.bright(horseColor, -40, -30));
		tesselator.point(-300 + zof2, -100, z + fana);
		tesselator.point(-220 + zof2, -100, z + fana);
		tesselator.point(-250 + zof3, -150, z + fana);

		tesselator.color(Utility.adjustBrightness(horseColor, -50));
		tesselator.point(-300 + zof2, -100, z + fana);
		tesselator.point(-250 + zof3, -150, z + fana);
		tesselator.point(-260 + zoff, -225 + lift, z + fana);

		tesselator.color(Utility.adjustBrightness(horseColor, -40));
		tesselator.point(-260 + zoff, -225 + lift, z + fana);
		tesselator.point(-300 + zof2, -100, z + fana);
		tesselator.point(-300 + zoff, -225 + lift, z + fana);

		// outside part
		float mid = -250 + zof3;
		float upp = -300 + zof2;
		tesselator.color(rand.bright(horseColor, -30, -10));
		tesselator.point(-315, -35, z + fana);
		tesselator.point(-315, -35, z);
		tesselator.point(upp, -100, z);

		tesselator.color(rand.bright(horseColor, -30, -10));
		tesselator.point(-315, -35, z + fana);
		tesselator.point(upp, -100, z + fana);
		tesselator.point(upp, -100, z);

		tesselator.color(rand.bright(horseColor, -40, -25));
		tesselator.point(upp, -100, z);
		tesselator.point(mid, -150, z);
		tesselator.point(mid, -150, z + fana);

		tesselator.color(rand.bright(horseColor, -40, -25));
		tesselator.point(upp, -100, z);
		tesselator.point(upp, -100, z + fana);
		tesselator.point(mid, -150, z + fana);

		tesselator.color(rand.bright(horseColor, -50, -37));
		tesselator.point(-300 + zoff, -225 + lift, z);
		tesselator.point(mid, -150, z);
		tesselator.point(mid, -150, z + fana);

		tesselator.color(rand.bright(horseColor, -60, -37));
		tesselator.point(-300 + zoff, -225 + lift, z);
		tesselator.point(-300 + zoff, -225 + lift, z + fana);
		tesselator.point(mid, -150, z + fana);
	}

	private float rot = 0;
	private float tar = 0;
	private Rand rand = new Rand();
	private float tail = 0;
	public void tick() {
		if (tar == 0 && rand.nextDouble() < 0.006) {
			tar = rand.next2PI() * rand.nextNegate();
		}
		if (tar != 0) {
			if (tar > rot) {
				rot += 0.01f;
			}
			if (tar < rot) {
				rot -= 0.01f;
			}
			if (Math.abs(tar - rot) < 0.1)
				tar = 0;
		}
		tail += 0.02f;
	}

	public PointTesselator getTesselator() {
		return tesselator;
	}
}