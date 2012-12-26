package org.imaginea.mviewer.common;

public interface DocReader {
int getnumberOfRows();
int getnumberOfColumns();
String getvalue(int rowNumber,int columnNumber);
}
