package front_end;

import front_end.types.FUNCTION;
import front_end.types.IDENTIFIER;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

  public Map<String, Integer> stackOffsets = new HashMap<>();
  public Map<String, IDENTIFIER> dictionary = new HashMap<>();
  SymbolTable parentST;
  private int nextOffset;
  private int currJumpOffset = 0;
  private int frameSize;

  public SymbolTable(SymbolTable parentST) {
    this.parentST = parentST;
  }

  public void add(String name, IDENTIFIER obj) {
    dictionary.put(name, obj);
  }

  public IDENTIFIER lookup(String name) {
    return dictionary.get(name);
  }

  public IDENTIFIER lookupAll(String name) {
    SymbolTable ST = this;
    while (ST != null) {
      IDENTIFIER obj = ST.lookup(name);
      if (obj != null) {
        return obj;
      }
      ST = ST.parentST;
    }

    return null;
  }

  public int getIdentOffset(String name) {
    int offset = currJumpOffset;
    SymbolTable S = this;
    while (!S.stackOffsets.containsKey(name)) {
      offset += S.frameSize - 4;
      S = S.parentST;
    }
    offset += S.stackOffsets.get(name);
    return offset;
  }

  public int setFrameSize() {
    int size = 0;
    for (IDENTIFIER ident : dictionary.values()) {
      if (!(ident instanceof FUNCTION || ident == null)) {
        size += 4;
      }
    }
    frameSize = size;
    nextOffset = frameSize - 4;
    return frameSize;
  }

  public int getFrameSize() {
    return frameSize;
  }

  public int storeVariable(String name) {
    assert (nextOffset >= 0);
    stackOffsets.put(name, nextOffset);
    int ret = nextOffset;
    if (nextOffset > 0) {
      nextOffset -= 4;
    }
    return ret;
  }

  public void pushOffset() {
    currJumpOffset += 4;
  }

  public void popOffset() {
    currJumpOffset -= 4;
  }

  public void resetOffset() {
    currJumpOffset = 0;
  }

  public int getJumpOffset() {
    return currJumpOffset;
  }

  public SymbolTable getParentST() {
    return parentST;
  }
}
