package whilelang.interp;
import whilelang.ast.*;
import java.io.IOException;
import java.util.*;

public class Interpreter implements whilelang.ast.Visitor<Integer> {
	List<Map<String,Integer> > maps = new ArrayList<>();

	public  void pushMap(){
		Map<String,Integer> newMap = new HashMap<String,Integer>();
		maps.add(newMap);
	}

	public void updateValue(String varID, Integer value){
		for(int i = maps.size()-1; i >= 0; i--) {
			Map<String, Integer> map = maps.get(i);
			boolean varIsExist = map.containsKey(varID);
			if (varIsExist) {
				map.put(varID, value);
				return;
			}
		}
	}

	public void pushVar(String varID,Integer value){
		Map<String,Integer> updatedMap = maps.get(maps.size()-1);
		if(getValue(varID) == null) {
			updatedMap.put(varID,value);
			maps.set(maps.size()-1,updatedMap);
		}
		else
			updateValue(varID, value);

	}

	public Integer getValue(String varID){
		for(int i = maps.size()-1; i >= 0; i--){
			Map<String,Integer> map = maps.get(i);
			boolean varIsExist = map.containsKey(varID);
			if(varIsExist)
				return map.get(varID);
		}
		return  null;
	}

	public void popMap(){
		maps.remove(maps.size()-1);
	}

	public Integer visit(Print n) {
//		System.out.println("in Print");
		// TODO Implement this!
		System.out.println(n.msg + getValue(n.varID));
		return null;
	}

	public Integer visit(Assign n) {
//		System.out.println("in Assign");
        // TODO Implement this!
		int result = n.expr.accept(this);
		pushVar(n.varID, result);
        return result;
    }

	public Integer visit(Skip n) {
//		System.out.println("in Skip");
		// TODO Implement this!
		return null;
	}

	public Integer visit(Block n) {
//		System.out.println("in Block");
		// TODO Implement this!
		pushMap();
		for( int i = 0; i < n.body.size(); i++){
			n.body.get(i).accept(this);
		}
		popMap();
		return null;
	}

	public Integer visit(IfThenElse n) {
//		System.out.println("in If");
		// TODO Implement this!
		int condition = n.expr.accept(this);
		if( condition == 1 )
			n.then.accept(this);
		else
			n.elze.accept(this);
		return null;
	}

	public Integer visit(While n) {
//		System.out.println("in While");
		// TODO Implement this!
		while(n.expr.accept(this) == 1)
			n.body.accept(this);
		return null;
	}

	public Integer visit(For n) {
//		System.out.println("in For");
		// TODO Implement this!
		for(n.init.accept(this);n.expr.accept(this) == 1;n.step.accept(this)) {
			n.body.accept(this);
		}
		return null;
	}


	public Integer visit(Var n) {
//		System.out.println("in Var");
		// TODO Implement this!
		int result = getValue(n.varID);
		return result;
	}

	public Integer visit(IntLiteral n) {
//		System.out.println("in int");
		// TODO Implement this!

		return n.value;
	}

	public Integer visit(Plus n) {
//		System.out.println("in Plus");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		return lhside + rhside;
	}

	public Integer visit(Minus n) {
//		System.out.println("in Minus");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		return lhside - rhside;
	}

	public Integer visit(Times n) {
//		System.out.println("in Times");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		return lhside * rhside;
	}

	public Integer visit(Division n) {
//		System.out.println("in Division");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		return lhside / rhside;
	}

	public Integer visit(Modulo n) {
//		System.out.println("in Modulo");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		return lhside % rhside;
	}

	public Integer visit(Equals n) {
//		System.out.println("in Equals");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		if (lhside == rhside)
			return 1;
		else
			return 0;
	}

	public Integer visit(GreaterThan n) {
//		System.out.println("in GT");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		if (lhside > rhside)
			return 1;
		else
			return 0;
	}

	public Integer visit(LessThan n) {
//		System.out.println("in LT");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);

		if (lhside < rhside)
			return 1;
		else
			return 0;
	}

	public Integer visit(And n) {
//		System.out.println("in And");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		if ((lhside==1) && (rhside==1))
			return 1;
		else
			return 0;
	}

	public Integer visit(Or n) {
//		System.out.println("in Or");
		// TODO Implement this!
		int lhside = n.lhs.accept(this);
		int rhside = n.rhs.accept(this);
		if ((lhside==1) || (rhside==1))
			return 1;
		else
			return 0;
	}

	public Integer visit(Neg n) {
//		System.out.println("in Neg");
		// TODO Implement this!
		int value = n.expr.accept(this);
		return -value;
	}

	public Integer visit(Not n) {
//		System.out.println("in Not");
		// TODO Implement this!
		int value = n.expr.accept(this);
		if(value==1)
			return 0;
		else
			return 1;
	}

	public Integer visit(UnaryExpression n) {
//		System.out.println("in Unary");
		// TODO Implement this!
		return null;
	}

	public Integer visit(BinaryExpression n) {
//		System.out.println("in Binary");
		// TODO Implement this!
		return null;
	}
}