import sys
from antlr4 import *
from labeldExprLexer import labeldExprLexer
from labeldExprParser import labeldExprParser
from EvalVisitor import EvalVisitor

def main(argv):
    if len(argv) > 1:
        input_stream = FileStream(argv[1])
    else:
        input_stream = InputStream(sys.stdin.read())

    lexer = labeldExprLexer(input_stream)
    stream = CommonTokenStream(lexer)
    parser = labeldExprParser(stream)
    tree = parser.prog()

    visitor = EvalVisitor()
    visitor.visit(tree)

if __name__ == '__main__':
    main(sys.argv)

