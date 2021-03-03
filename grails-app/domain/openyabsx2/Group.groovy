package openyabsx2

class Group {

    final static String ROOT = "/"

    String name
    String importid
    Group parent

    static hasMany = [children: Group]
    static mappedBy = [children: 'parent']

    static mapping = {
        table 'groups'
    }

    static constraints = {
        name blank: false, unique: true
        parent nullable: true
        importid nullable: true
    }

    Group getRootNode() {
        if (parent) {
            //if parent is not null then by definition this node is a child node of the tree.
            return parent.getRootNode()
        } else {
            //if parent is null then by definition it is the root node.
            return this
        }
    }

    boolean isLeaf() {
        //determines if this node is a leaf node. a leaf is a node with zero children
        return children.isEmpty()
    }

    String toString() {
        if (name == ROOT || parent == null) return name;
        if (parent.name == ROOT) return ROOT + name
        return parent.toString() + ROOT + name
    }
}