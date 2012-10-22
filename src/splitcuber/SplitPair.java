package splitcuber;

public class SplitPair<L, R> {
    private final L left;
    private final R right;

    public SplitPair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof SplitPair))
            return false;
        @SuppressWarnings("rawtypes")
        SplitPair pairo = (SplitPair) o;
        return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
    }

}
