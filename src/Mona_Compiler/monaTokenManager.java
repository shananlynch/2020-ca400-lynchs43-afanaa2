/* Generated By:JJTree&JavaCC: Do not edit this line. monaTokenManager.java */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/** Token Manager. */
public class monaTokenManager implements monaConstants
{
     static int commentNesting = 0;

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 9:
         jjmatchedKind = 2;
         return jjMoveNfa_0(5, 0);
      case 10:
         jjmatchedKind = 3;
         return jjMoveNfa_0(5, 0);
      case 12:
         jjmatchedKind = 5;
         return jjMoveNfa_0(5, 0);
      case 13:
         jjmatchedKind = 4;
         return jjMoveNfa_0(5, 0);
      case 32:
         jjmatchedKind = 1;
         return jjMoveNfa_0(5, 0);
      case 33:
         return jjMoveStringLiteralDfa1_0(0x400000000000000L);
      case 37:
         jjmatchedKind = 53;
         return jjMoveNfa_0(5, 0);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x100000000000000L);
      case 40:
         jjmatchedKind = 42;
         return jjMoveNfa_0(5, 0);
      case 41:
         jjmatchedKind = 43;
         return jjMoveNfa_0(5, 0);
      case 42:
         jjmatchedKind = 50;
         return jjMoveNfa_0(5, 0);
      case 43:
         jjmatchedKind = 48;
         return jjMoveNfa_0(5, 0);
      case 44:
         jjmatchedKind = 37;
         return jjMoveNfa_0(5, 0);
      case 45:
         jjmatchedKind = 49;
         return jjMoveNfa_0(5, 0);
      case 46:
         jjmatchedKind = 46;
         return jjMoveStringLiteralDfa1_0(0x7c0000000L);
      case 47:
         jjmatchedKind = 51;
         return jjMoveStringLiteralDfa1_0(0x40L);
      case 58:
         jjmatchedKind = 39;
         return jjMoveNfa_0(5, 0);
      case 59:
         jjmatchedKind = 38;
         return jjMoveNfa_0(5, 0);
      case 60:
         jjmatchedKind = 59;
         return jjMoveStringLiteralDfa1_0(0x1000000000000000L);
      case 61:
         jjmatchedKind = 47;
         return jjMoveStringLiteralDfa1_0(0x200000000000000L);
      case 62:
         jjmatchedKind = 61;
         return jjMoveStringLiteralDfa1_0(0x4000000000000000L);
      case 91:
         jjmatchedKind = 44;
         return jjMoveNfa_0(5, 0);
      case 93:
         jjmatchedKind = 45;
         return jjMoveNfa_0(5, 0);
      case 94:
         jjmatchedKind = 52;
         return jjMoveNfa_0(5, 0);
      case 66:
      case 98:
         return jjMoveStringLiteralDfa1_0(0x110000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa1_0(0x5000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa1_0(0xc00000L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa1_0(0x28020000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa1_0(0x2208000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa1_0(0x1800000000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa1_0(0x4000000L);
      case 86:
      case 118:
         return jjMoveStringLiteralDfa1_0(0x40800L);
      case 87:
      case 119:
         return jjMoveStringLiteralDfa1_0(0x10000000L);
      case 123:
         jjmatchedKind = 40;
         return jjMoveNfa_0(5, 0);
      case 124:
         return jjMoveStringLiteralDfa1_0(0x80000000000000L);
      case 125:
         jjmatchedKind = 41;
         return jjMoveNfa_0(5, 0);
      case 126:
         jjmatchedKind = 54;
         return jjMoveNfa_0(5, 0);
      default :
         return jjMoveNfa_0(5, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 0);
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x100000000000000L) != 0L)
         {
            jjmatchedKind = 56;
            jjmatchedPos = 1;
         }
         break;
      case 47:
         if ((active0 & 0x40L) != 0L)
         {
            jjmatchedKind = 6;
            jjmatchedPos = 1;
         }
         break;
      case 61:
         if ((active0 & 0x200000000000000L) != 0L)
         {
            jjmatchedKind = 57;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x400000000000000L) != 0L)
         {
            jjmatchedKind = 58;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x1000000000000000L) != 0L)
         {
            jjmatchedKind = 60;
            jjmatchedPos = 1;
         }
         else if ((active0 & 0x4000000000000000L) != 0L)
         {
            jjmatchedKind = 62;
            jjmatchedPos = 1;
         }
         break;
      case 65:
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x8080800L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 70:
      case 102:
         if ((active0 & 0x200000L) != 0L)
         {
            jjmatchedKind = 21;
            jjmatchedPos = 1;
         }
         break;
      case 71:
      case 103:
         return jjMoveStringLiteralDfa2_0(active0, 0xc0000000L);
      case 72:
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000L);
      case 75:
      case 107:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x300c24000L);
      case 78:
      case 110:
         if ((active0 & 0x2000000L) != 0L)
         {
            jjmatchedKind = 25;
            jjmatchedPos = 1;
         }
         return jjMoveStringLiteralDfa2_0(active0, 0x8000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x20051000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x5100000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000000L);
      case 124:
         if ((active0 & 0x80000000000000L) != 0L)
         {
            jjmatchedKind = 55;
            jjmatchedPos = 1;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(5, 1);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 1);
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x3c0100000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x10110c0000L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x400001000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x30000L);
      case 82:
      case 114:
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 11;
            jjmatchedPos = 2;
         }
         else if ((active0 & 0x20000000L) != 0L)
         {
            jjmatchedKind = 29;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0xc00000L);
      case 84:
      case 116:
         if ((active0 & 0x8000L) != 0L)
         {
            jjmatchedKind = 15;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000000L);
      default :
         break;
   }
   return jjMoveNfa_0(5, 2);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 2);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 2);
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x120000L);
      case 68:
      case 100:
         if ((active0 & 0x40000L) != 0L)
         {
            jjmatchedKind = 18;
            jjmatchedPos = 3;
         }
         break;
      case 69:
      case 101:
         if ((active0 & 0x400000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 3;
         }
         else if ((active0 & 0x4000000L) != 0L)
         {
            jjmatchedKind = 26;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x800000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000000L);
      case 76:
      case 108:
         if ((active0 & 0x10000L) != 0L)
         {
            jjmatchedKind = 16;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000L);
      case 78:
      case 110:
         if ((active0 & 0x80000L) != 0L)
         {
            jjmatchedKind = 19;
            jjmatchedPos = 3;
         }
         else if ((active0 & 0x200000000L) != 0L)
         {
            jjmatchedKind = 33;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x101000000L);
      case 80:
      case 112:
         if ((active0 & 0x1000000000L) != 0L)
         {
            jjmatchedKind = 36;
            jjmatchedPos = 3;
         }
         break;
      case 83:
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x408005000L);
      case 84:
      case 116:
         if ((active0 & 0x40000000L) != 0L)
         {
            jjmatchedKind = 30;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      default :
         break;
   }
   return jjMoveNfa_0(5, 3);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 3);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 3);
   }
   switch(curChar)
   {
      case 95:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      case 69:
      case 101:
         if ((active0 & 0x8000000L) != 0L)
         {
            jjmatchedKind = 27;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x10000000L) != 0L)
         {
            jjmatchedKind = 28;
            jjmatchedPos = 4;
         }
         return jjMoveStringLiteralDfa5_0(active0, 0x400000000L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000L);
      case 75:
      case 107:
         if ((active0 & 0x100000L) != 0L)
         {
            jjmatchedKind = 20;
            jjmatchedPos = 4;
         }
         break;
      case 78:
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 83:
      case 115:
         if ((active0 & 0x4000L) != 0L)
         {
            jjmatchedKind = 14;
            jjmatchedPos = 4;
         }
         break;
      case 84:
      case 116:
         if ((active0 & 0x1000L) != 0L)
         {
            jjmatchedKind = 12;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x20000L) != 0L)
         {
            jjmatchedKind = 17;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x1000000L) != 0L)
         {
            jjmatchedKind = 24;
            jjmatchedPos = 4;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(5, 4);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 4);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 4);
   }
   switch(curChar)
   {
      case 71:
      case 103:
         if ((active0 & 0x800000000L) != 0L)
         {
            jjmatchedKind = 35;
            jjmatchedPos = 5;
         }
         break;
      case 72:
      case 104:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 78:
      case 110:
         if ((active0 & 0x2000L) != 0L)
         {
            jjmatchedKind = 13;
            jjmatchedPos = 5;
         }
         break;
      case 82:
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x400000000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa6_0(active0, 0x100000000L);
      default :
         break;
   }
   return jjMoveNfa_0(5, 5);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 5);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 5);
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x80000000L);
      case 70:
      case 102:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 6;
         }
         break;
      case 72:
      case 104:
         if ((active0 & 0x100000000L) != 0L)
         {
            jjmatchedKind = 32;
            jjmatchedPos = 6;
         }
         break;
      case 84:
      case 116:
         if ((active0 & 0x400000000L) != 0L)
         {
            jjmatchedKind = 34;
            jjmatchedPos = 6;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(5, 6);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjMoveNfa_0(5, 6);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
   return jjMoveNfa_0(5, 6);
   }
   switch(curChar)
   {
      case 82:
      case 114:
         if ((active0 & 0x80000000L) != 0L)
         {
            jjmatchedKind = 31;
            jjmatchedPos = 7;
         }
         break;
      default :
         break;
   }
   return jjMoveNfa_0(5, 7);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int strKind = jjmatchedKind;
   int strPos = jjmatchedPos;
   int seenUpto;
   input_stream.backup(seenUpto = curPos + 1);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { throw new Error("Internal Error"); }
   curPos = 0;
   int startsAt = 0;
   jjnewStateCnt = 27;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 5:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 65)
                        kind = 65;
                     jjCheckNAddStates(0, 4);
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 65)
                        kind = 65;
                     jjCheckNAdd(22);
                  }
                  else if (curChar == 45)
                     jjCheckNAddStates(5, 8);
                  else if (curChar == 34)
                     jjCheckNAddStates(9, 11);
                  else if (curChar == 46)
                     jjCheckNAdd(7);
                  else if (curChar == 47)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 0:
                  if (curChar == 47)
                     jjCheckNAddStates(12, 14);
                  break;
               case 1:
                  if ((0xffffffffffffdbffL & l) != 0L)
                     jjCheckNAddStates(12, 14);
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 7)
                     kind = 7;
                  break;
               case 3:
                  if (curChar == 10 && kind > 7)
                     kind = 7;
                  break;
               case 4:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 6:
                  if (curChar == 46)
                     jjCheckNAdd(7);
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 66)
                     kind = 66;
                  jjCheckNAdd(7);
                  break;
               case 9:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 67)
                     kind = 67;
                  jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 10:
               case 14:
                  if (curChar == 34)
                     jjCheckNAddStates(9, 11);
                  break;
               case 11:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddStates(9, 11);
                  break;
               case 12:
                  if (curChar == 34 && kind > 68)
                     kind = 68;
                  break;
               case 16:
                  if (curChar == 45)
                     jjCheckNAddStates(5, 8);
                  break;
               case 17:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAddTwoStates(17, 18);
                  break;
               case 18:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(18);
                  break;
               case 19:
                  if (curChar == 48 && kind > 65)
                     kind = 65;
                  break;
               case 20:
                  if ((0x3fe000000000000L & l) != 0L)
                     jjCheckNAddStates(15, 17);
                  break;
               case 21:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(21, 22);
                  break;
               case 22:
                  if (curChar != 46)
                     break;
                  if (kind > 66)
                     kind = 66;
                  jjCheckNAdd(23);
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 66)
                     kind = 66;
                  jjCheckNAdd(23);
                  break;
               case 24:
                  if (curChar == 48)
                     jjCheckNAdd(22);
                  break;
               case 25:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAddStates(0, 4);
                  break;
               case 26:
                  if (curChar != 48)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(22);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 5:
               case 9:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 67)
                     kind = 67;
                  jjCheckNAdd(9);
                  break;
               case 1:
                  jjAddStates(12, 14);
                  break;
               case 11:
                  if ((0xffffffffefffffffL & l) != 0L)
                     jjCheckNAddStates(9, 11);
                  break;
               case 13:
                  if (curChar == 92)
                     jjAddStates(18, 19);
                  break;
               case 15:
                  if (curChar == 92)
                     jjCheckNAddStates(9, 11);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(12, 14);
                  break;
               case 11:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(9, 11);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 27 - (jjnewStateCnt = startsAt)))
         break;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { break; }
   }
   if (jjmatchedPos > strPos)
      return curPos;

   int toRet = Math.max(curPos, seenUpto);

   if (curPos < toRet)
      for (i = toRet - Math.min(curPos, seenUpto); i-- > 0; )
         try { curChar = input_stream.readChar(); }
         catch(java.io.IOException e) { throw new Error("Internal Error : Please send a bug report."); }

   if (jjmatchedPos < strPos)
   {
      jjmatchedKind = strKind;
      jjmatchedPos = strPos;
   }
   else if (jjmatchedPos == strPos && jjmatchedKind > strKind)
      jjmatchedKind = strKind;

   return toRet;
}
static private int jjMoveStringLiteralDfa0_1()
{
   switch(curChar)
   {
      case 35:
         return jjMoveStringLiteralDfa1_1(0x200L);
      case 47:
         return jjMoveStringLiteralDfa1_1(0x100L);
      default :
         return 1;
   }
}
static private int jjMoveStringLiteralDfa1_1(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      return 1;
   }
   switch(curChar)
   {
      case 35:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(1, 8);
         break;
      case 47:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(1, 9);
         break;
      default :
         return 2;
   }
   return 2;
}
static final int[] jjnextStates = {
   17, 18, 20, 21, 22, 17, 19, 20, 24, 11, 12, 13, 1, 2, 4, 20, 
   21, 22, 14, 15, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, "\54", "\73", "\72", 
"\173", "\175", "\50", "\51", "\133", "\135", "\56", "\75", "\53", "\55", "\52", 
"\57", "\136", "\45", "\176", "\174\174", "\46\46", "\75\75", "\41\75", "\74", 
"\74\75", "\76", "\76\75", null, null, null, null, null, null, };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "IN_COMMENT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x7ffffffffffff801L, 0x1eL, 
};
static final long[] jjtoSkip = {
   0x7feL, 0x0L, 
};
static protected SimpleCharStream input_stream;
static private final int[] jjrounds = new int[27];
static private final int[] jjstateSet = new int[54];
private static final StringBuilder jjimage = new StringBuilder();
private static StringBuilder image = jjimage;
private static int jjimageLen;
private static int lengthOfMatch;
static protected char curChar;
/** Constructor. */
public monaTokenManager(SimpleCharStream stream){
   if (input_stream != null)
      throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);
   input_stream = stream;
}

/** Constructor. */
public monaTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
static private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 27; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
static public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
static public void SwitchTo(int lexState)
{
   if (lexState >= 2 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }
   image = jjimage;
   image.setLength(0);
   jjimageLen = 0;

   switch(curLexState)
   {
     case 0:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       if (jjmatchedPos == 0 && jjmatchedKind > 10)
       {
          jjmatchedKind = 10;
       }
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
           SkipLexicalActions(null);
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
           continue EOFLoop;
        }
     }
     int error_line = input_stream.getEndLine();
     int error_column = input_stream.getEndColumn();
     String error_after = null;
     boolean EOFSeen = false;
     try { input_stream.readChar(); input_stream.backup(1); }
     catch (java.io.IOException e1) {
        EOFSeen = true;
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
        if (curChar == '\n' || curChar == '\r') {
           error_line++;
           error_column = 0;
        }
        else
           error_column++;
     }
     if (!EOFSeen) {
        input_stream.backup(1);
        error_after = curPos <= 1 ? "" : input_stream.GetImage();
     }
     throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      case 3 :
         image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
             mona.line++;
         break;
      case 6 :
         image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
            commentNesting++;
         break;
      case 7 :
         image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
      mona.line++;
         break;
      case 8 :
         image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
            commentNesting++;
         break;
      case 9 :
         image.append(input_stream.GetSuffix(jjimageLen + (lengthOfMatch = jjmatchedPos + 1)));
              commentNesting--;
          if (commentNesting == 0)
               SwitchTo(DEFAULT);
         break;
      default :
         break;
   }
}
static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}
