package avl;

/* Author: Ashton Finch
 * Date: 07/15/2025
 * Description: A program to simulate BSTs and AVLs including rotations. */

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }

  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  // compareTo: comes before it is -, after is +
  private void bstInsert(Node n, String w) {
    int c = w.compareTo(n.word);
    if (c < 0) {
      if (n.left == null) {
        n.left = new Node(w, n);
        size++;
      } else {
        bstInsert(n.left, w);
      }
    } else if (c > 0) {
      if (n.right == null) {
        n.right = new Node(w, n);
        size++;
      } else {
        bstInsert(n.right, w);
      }
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  }

  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    if (search(w) != null) {
      return;
    }
    if (w.compareTo(n.word) < 0) {
      if (n.left == null) {
        n.left = new Node(w);
        n.left.parent = n;
        n.left.height = 0;
        size++;
      } else {
        avlInsert(n.left, w);
      }
    } else if (w.compareTo(n.word) > 0) {
      if (n.right == null) {
        n.right = new Node(w);
        n.right.parent = n;
        n.right.height = 0;
        size++;
      } else {
        avlInsert(n.right, w);
      }
    } else {
      return;
    }

    n.height = getHeight(n);
    rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;

    if (y.left != null) {
      y.left.parent = x;
    }

    y.parent = x.parent;

    if (x.parent == null) {
      root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }

    y.left = x;
    x.parent = y;
    x.height = getHeight(x);
    y.height = getHeight(y);
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right;

    if (x.right != null) {
      x.right.parent = y;
    }

    x.parent = y.parent;

    if (y.parent == null) {
      root = x;
    } else if (y == y.parent.left) {
      y.parent.left = x;
    } else {
      y.parent.right = x;
    }

    x.right = y;
    y.parent = x;
    x.height = getHeight(x);
    y.height = getHeight(y);
  }
  

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    if (getBalance(n) < -1) {
      if (getBalance(n.left) <= 0) {
        rightRotate(n);
      } else {
        leftRotate(n.left);
        rightRotate(n);
      }
    } else if (getBalance(n) > 1) {
      if (getBalance(n.right) >= 0) {
        leftRotate(n);
      } else {
        rightRotate(n.right);
        leftRotate(n);
      }
    }
  }

  // Returns -1 if the starting node is null.
  // Otherwise returns the height of n by looking 
  // at left and right heights.
  public int getHeight(Node n) {
    if (n == null) {
      return -1;
    } else {
      int lHeight = -1;
      int rHeight = -1;

      if (n.left != null) {
        lHeight = n.left.height;
      }

      if (n.right != null) {
        rHeight = n.right.height;
      }

      if (lHeight > rHeight) {
        return 1 + lHeight;
      } else {
        return 1 + rHeight;
      }
    }
  }

  // Returns the balance of n by returning the right height - left.
  public int getBalance(Node n) {
    int rHeight = getHeight(n.right);
    int lHeight = getHeight(n.left);
    return rHeight - lHeight;
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
