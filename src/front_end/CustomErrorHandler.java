package front_end;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

class CustomErrorHandler extends BaseErrorListener {

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
      int charPositionInLine, String msg, RecognitionException e) {
    Main.EXIT_CODE = 100;
    //   System.err.println("SYNTAX ERROR:" + line + ": " + charPositionInLine);
  }
}
