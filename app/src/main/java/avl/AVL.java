package avl;

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
      if (n.left == null) {
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
    // TODO
    int c = w.compareTo(n.word);
    if (c < 0) {
      if (n.left == null) {
        n.left = new Node(w, n);
        size++;
      } else {
        avlInsert(n.left, w);
      }
    } else if (c > 0) {
      if (n.left == null) {
        n.right = new Node(w, n);
        size++;
      } else {
        avlInsert(n.right, w);
      }
    }

    rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    if (x.right == null) {
      return;
    }

    Node root2 = x.right;

    if (x.parent == null) {
      root = root2;
      root2.parent = null;
    } else {
      root2.parent = x.parent;
      if (root2.parent.right == x) {
        root2.parent.right = root2;
      } else {
        root2.parent.left = root2;
      }
    }


    root2.left = x;
    x.parent = root2;
    x.right = x.right.left;
    x.right.left.parent = x;
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    if (y.left == null) {
      return;
    }

    Node root2 = y.left;

    if (x.parent == null) {
      root = root2;
      root2.parent = null;
    } else {
      root2.parent = y.parent;
      if (root2.parent.right == y) {
        root2.parent.right = root2;
      } else {
        root2.parent.left = root2;
      }
    }

    root2.right = y;
    y.parent = root2;
    y.left = y.left.right;
    y.left.right.parent = y;
  }
  

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    if (getBalance(n) > 1) {
      if (getBalance(n.right) < 0) {
        rightRotate(n.right);
        leftRotate(n);
      } else {
        leftRotate(n);
      }
    } else if (getBalance(n) < -1) {
      if (getBalance(n.left) > 0) {
        leftRotate(n.left);
        rightRotate(n);
      } else {
        rightRotate(n);
      }
    } else { // n is null
      return;
    }
  }

  // Gets the height of the node recursively by adding 1
  // plus the height of the left and right nodes until
  // left and right are null.
  // Returns -1 if the starting node is null.
  // Otherwise returns the height of n.
  public int getHeight(Node n) {
    if (n == null) {
      return -1;
    } else if (n.left == null && n.right == null) {
      return 0;
    } else if (n.left == null && n.right != null) {
      return 1 + getHeight(n.right);
    } else if (n.left != null && n.right == null) {
      return 1 + getHeight(n.left);
    } else if (n.left.height >= n.right.height) {
      return 1 + getHeight(n.left);
    } else {
      return 1 + getHeight(n.right);
    }
  }

  // Returns the balance of n by returning the right height - left.
  // If n is null just returns a balance of 0.
  public int getBalance(Node n) {
    if (n != null) {
      int rHeight = getHeight(n.right);
      int lHeight = getHeight(n.left);
      return rHeight - lHeight;
    } else {
      return 0;
    }
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
