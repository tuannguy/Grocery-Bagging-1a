# Grocery-Bagging-1a
Cs 457 Project 1
Connor Wood
Tuan Nguyen
Lam Nguyen



Design

(Connor) -- I had created a simple file parser to create a list of Item-objects from an input file and pass on each description to the item it belongs to.  In other words, it detects the first word in an item line as the grocery's name, then considers the constraint it may posses, followed by adding the list of items that it can only be bagged/not be bagged with to that respective
grocery.  The list of items constrained to the grocery in question are put as strings as to make processing and lookups easier.



Testing

(Connor) -- With the file parser, I made sure to include code that considers invalid inputs.  For example, since each grocery should only contain a single '+' or '-', then if there are either both or more than one of either constraint, the program will send an error message then exit.  Similarly, if there's to be a list of items at all, there needs to be a constraint to begin with.  So thankfully the parser checks to make sure a list of items does not follow without a single '+' or '-' appearing first.
