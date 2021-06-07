public class BipolarSigmoidFunction implements ActivatorFunction
{
    public BipolarSigmoidFunction() { }

    @Override
    public Float activate(Float signal) {
        return (2.0F / (1.0F + (float) Math.pow(Math.E, -signal))) - 1.0F;
    }

    @Override
    public Float derived(Float signal) {
        return activate(signal) * (1 - activate(signal));
    }
}
