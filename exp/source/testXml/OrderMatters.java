/*
 * 创建日期 2005-4-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package testXml;

/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
import statement.StatementDocument;
import statement.StatementDocument.Statement;
import statement.Transaction;
import java.io.File;

import org.apache.xmlbeans.XmlCursor;

import javax.xml.namespace.QName;

public class OrderMatters
{
	private static QName deposit = new QName( "http://statement", "deposit" );

	public static void main ( String[] args ) throws Exception
	{
		// load the xml instance into the store and return a
		// strongly typed instance of StatementDocument
		StatementDocument stmtDoc = StatementDocument.Factory.parse( new File( "D:/eclipse/xmlbean/xmlbeans-2.0.0-beta1/samples/OrderMatters/xml/stmt.xml" ) );

		System.out.println( "Valid statement instance? " + stmtDoc.validate() );

		float balance = balanceOutOfOrder(stmtDoc);

		System.out.println( "Ending balance: $" + balance );

		balance = balanceInOrder(stmtDoc);

		System.out.println( "Ending balance: $" + balance );
	}

	/**
	 * Out of order balance: the ease of stronly-typed XmlObjects!
	 */
	public static float balanceOutOfOrder(StatementDocument stmtDoc)
	{
		Statement stmt = stmtDoc.getStatement();

		float balance = 0;

		Transaction[] deposits    = stmt.getDepositArray();
		Transaction[] withdrawals = stmt.getWithdrawalArray();

		for ( int i = 0 ; i < deposits.length ; i++ )
			balance += deposits[ i ].getAmount();

		for ( int i = 0 ; i < withdrawals.length ; i++ )
			balance -= withdrawals[ i ].getAmount();

		return balance;
	}

	/**
	 * In order balance: the power of XmlCursor!
	 */
	public static float balanceInOrder(StatementDocument stmtDoc)
	{
		float balance = 0;

		XmlCursor cursor = stmtDoc.newCursor();

		// use xpath to select elements
		cursor.selectPath( "*/*" );

		// iterate over the selection
		while ( cursor.toNextSelection() )
		{
			// two views of the same data:
			// move back and forth between XmlObject <-> XmlCursor
			Transaction trans = (Transaction) cursor.getObject();

			float amount = trans.getAmount();

			if (cursor.getName().equals( deposit ))
				balance += amount;
			else if ((balance -= amount) < 0)
			{
				// doh!
				System.out.println( "Negative balance: $" + balance );
				balance -= 50;
			}
		}

		return balance;
	}

}

