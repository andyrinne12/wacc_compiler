package front_end;

import front_end.types.BOOLEAN;
import front_end.types.CHAR;
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
      offset += S.frameSize;
      S = S.parentST;
    }
    offset += S.stackOffsets.get(name);
    return offset;
  }

  public int setFrameSize() {
    int size = 0;
    for (IDENTIFIER ident : dictionary.values()) {
      if (!(ident instanceof FUNCTION || ident == null)) {
        if (ident instanceof BOOLEAN || ident instanceof CHAR) {
          size += 1;
        } else {
          size += 4;
        }
      }
    }
    frameSize = size;
    // nextOffset = frameSize - 4;
    nextOffset = frameSize;
    return frameSize;
  }

  public int getFrameSize() {
    return frameSize;
  }

  public int storeVariable(String name) {
    assert (nextOffset >= 0);
    // stackOffsets.put(name, nextOffset);
    // int ret = nextOffset;
    // if (nextOffset > 0) {
    //   nextOffset -= 4;
    // }
    // return ret;

    // need to take into account what kind of data the variable is storing.
    // if storing a bool or a char, we just need to use 1 byte.
    IDENTIFIER ident = dictionary.get(name);
    if ((ident instanceof BOOLEAN) || (ident instanceof CHAR)) {
      nextOffset -= 1;
    } else {
      nextOffset -= 4;
    }
    stackOffsets.put(name, nextOffset);

    return nextOffset;
  }

  public void pushOffset(int size) {
    currJumpOffset += size;
  }

  public void popOffset(int size) {
    currJumpOffset -= size;
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
