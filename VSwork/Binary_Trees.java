import java.util.*;

public class Binary_Trees {
    static class Node
    {
        int data;
        Node left;
        Node right;
        Node(int data)
        {
            this.data=data;
            this.left=null;
            this.right=null;
        }
    }
    static class BinaryTree {
        static int indx=-1;
        public static Node buildTree(int nodes[])
        {
            indx++;
            if(nodes[indx]==-1)
            {
                return null;
            }
            Node newNode= new Node(nodes[indx]);
            newNode.left=buildTree(nodes);
            newNode.right=buildTree(nodes);
            return newNode;
        }
    }
    public static void Preorder_Traversal(Node rootNode)
    {
        if(rootNode==null)
        {
            System.out.print(-1+" ");
            return;
        }
        System.out.print(rootNode.data+" ");
        Preorder_Traversal(rootNode.left);
        Preorder_Traversal(rootNode.right);
        //we are printing root node at end
    }
    public static void Inorder_Traversal(Node rootNode){
        if(rootNode==null)
        {
            System.out.print(-1+" ");
            return;
        }
        Inorder_Traversal(rootNode.left);
        System.out.print(rootNode.data+" ");
        Inorder_Traversal(rootNode.right);
    }
    public static void Postorder_Traversal(Node rootNode){
        if(rootNode==null)
        {
            // System.out.print(-1+" ");
            return;
        }
        Postorder_Traversal(rootNode.left);
        Postorder_Traversal(rootNode.right);
        System.out.print(rootNode.data+" ");
        //we are printing root node at end
    }
    private static void Levelorder_Traversal(Node rootNode)
    {
        Queue<Node> que=new LinkedList<Node>();
        que.add(rootNode);
        que.add(null);
        while(!que.isEmpty())
        {
            Node currentNode=que.remove();
            if(currentNode==null)
            {
                System.out.println();
                if(que.isEmpty())
                {
                    break;
                }
                que.add(null);
            }
            else
            {
                System.out.print(currentNode.data+" ");
                if(currentNode.left!=null)
                {
                    que.add(currentNode.left);
                }
                if(currentNode.right!=null)
                {
                    que.add(currentNode.right);
                }
            }
        }
    }   
    public static void main(String[] args) {
        int node[]={1,2,4,-1,-1,5,-1,-1,3,-1,6,-1,-1};
        Node rootNode=BinaryTree.buildTree(node);
        // System.out.println(rootNode.data);
        Levelorder_Traversal(rootNode);
    }
}
