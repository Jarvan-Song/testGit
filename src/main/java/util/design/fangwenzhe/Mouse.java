package util.design.fangwenzhe;

/**
 * Created by songpanfei on 2020-05-12.
 */
public class Mouse  implements ComputerPart {

    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        computerPartVisitor.visit(this);
    }
}
