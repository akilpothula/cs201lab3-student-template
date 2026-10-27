import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     }

     // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {

          // split the traversal strings into arrays
          String[] t1 = traversal1.split("-");
          String[] t2 = traversal2.split("-");
          String[] t3 = traversal3.split("-");

          // check if all traversals have the same length
          if (t1.length != t2.length || t1.length != t3.length) {
               return "Invalid traversals";
          }

          // create a set to store unique elements from all traversals
          Set<String> uniqueElements = new HashSet<>();
          uniqueElements.addAll(Arrays.asList(t1));
          uniqueElements.addAll(Arrays.asList(t2));
          uniqueElements.addAll(Arrays.asList(t3));

          // check if the number of unique elements matches the length of the traversals
          if (uniqueElements.size() != t1.length) {
               return "Invalid traversals";
          }

          String[][] traversals = { t1, t2, t3 };

          for (int inorder = 0; inorder < 3; inorder++) {
               // skip if the current traversal is not a valid inorder traversal
               if (!isInorder(traversals[inorder])) {
                    continue;
               }

               // find the corresponding preorder and postorder traversals
               for (int preorder = 0; preorder < 3; preorder++) {

                    // // skip if this traversal is already being used as inorder
                    if (preorder == inorder) {
                         continue;
                    }

                    // find the postorder traversal index
                    int postorder = 3 - inorder - preorder;

                    if (validCombination(traversals[inorder], traversals[preorder], traversals[postorder])) {

                         String[] type = new String[3];

                         type[inorder] = "Inorder";
                         type[preorder] = "Preorder";
                         type[postorder] = "Postorder";

                         return "Traversal 1 - " + type[0]
                                   + ", Traversal 2 - " + type[1]
                                   + ", Traversal 3 - " + type[2];
                    }
               }
          }

          return "Invalid traversals";
     }

     private static boolean isInorder(String[] traversal) {

          // check if the traversal is in ascending order
          // if it is not, then it cannot be an inorder traversal
          for (int i = 1; i < traversal.length; i++) {

               int current = Integer.parseInt(traversal[i]);
               int previous = Integer.parseInt(traversal[i - 1]);

               if (current <= previous) {
                    return false;
               }
          }

          return true;
     }

     private static boolean validCombination(String[] inorder, String[] preorder, String[] postorder) {

          // check if the lengths of the traversals are equal
          return checkTree(
                    inorder,
                    preorder,
                    postorder,
                    0,
                    inorder.length - 1,
                    0,
                    preorder.length - 1,
                    0,
                    postorder.length - 1);
     }

     // this is to check if the given inorder, preorder and postorder traversals are valid for the same binary tree
     private static boolean checkTree(String[] inorder, String[] preorder,
               String[] postorder,
               int inStart,
               int inEnd,
               int preStart,
               int preEnd,
               int postStart,
               int postEnd) {

          if (inStart > inEnd) {
               return true;
          }

          if (preStart > preEnd || postStart > postEnd) {
               return false;
          }

          String root = preorder[preStart];

          if (!root.equals(postorder[postEnd])) {
               return false;
          }

          int rootIndex = -1;

          for (int i = inStart; i <= inEnd; i++) {

               if (inorder[i].equals(root)) {
                    rootIndex = i;
                    break;
               }
          }

          if (rootIndex == -1) {
               return false;
          }

          int leftSize = rootIndex - inStart;

          boolean left = checkTree(
                    inorder,
                    preorder,
                    postorder,
                    inStart,
                    rootIndex - 1,
                    preStart + 1,
                    preStart + leftSize,
                    postStart,
                    postStart + leftSize - 1);

          boolean right = checkTree(
                    inorder,
                    preorder,
                    postorder,
                    rootIndex + 1,
                    inEnd,
                    preStart + leftSize + 1,
                    preEnd,
                    postStart + leftSize,
                    postEnd - 1);

          return (left && right);
     }
}