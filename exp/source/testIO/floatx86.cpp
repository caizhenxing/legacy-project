
#pragma hdrstop
#include <condefs.h>
#include <stdio.h>
#include <conio.h>

//---------------------------------------------------------------------------
#pragma argsused
int main(int argc, char **argv)
{

  FILE* out;
  double data[500];
  double input[500];
  int n;

  out = fopen("le500.dat", "w+b");
  if (out != 0) {
    for (int i = 0; i < 500; i++) {
      data[i] = i/2.0 - 200.0;
    }
    n = fwrite(data, sizeof(double), 500, out);
    fclose(out);
    printf("Wrote %d doubles\n", n);
  }
  getch();
  FILE* in = fopen("le500.dat", "r+b");
  if (in != 0) {
    n = fread(input, sizeof(double), 500, in);
    fclose(in);
    printf("Read %d doubles\n", n);
    for (int i = 0; i < 500; i++) {
      printf("%d\t%e\n", i, input[i]);
    }
  }
  getch();

        return 0;
}
 