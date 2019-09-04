
package com.datasphere.government.gsp.datalineage;

import gudusoft.gsqlparser.EDbVendor;
//创建SQL Parser所必需的类以及SQL Parser在内部用于处理不同SQL方言的类

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.datasphere.government.gsp.datalineage.columnImpact.ColumnImpact;
import com.datasphere.government.gsp.datalineage.metadata.DDLParser;
import com.datasphere.government.gsp.datalineage.metadata.DDLSchema;
import com.datasphere.government.gsp.datalineage.metadata.ProcedureRelationScanner;
import com.datasphere.government.gsp.datalineage.metadata.ViewParser;
import com.datasphere.government.gsp.datalineage.model.ddl.schema.database;
import com.datasphere.government.gsp.datalineage.model.metadata.ColumnMetaData;
import com.datasphere.government.gsp.datalineage.model.metadata.MetaScanner;
import com.datasphere.government.gsp.datalineage.model.metadata.ProcedureMetaData;
import com.datasphere.government.gsp.datalineage.model.metadata.TableMetaData;
import com.datasphere.government.gsp.datalineage.model.xml.columnImpactResult;
import com.datasphere.government.gsp.datalineage.model.xml.procedureImpactResult;
import com.datasphere.government.gsp.datalineage.util.Pair;
import com.datasphere.government.gsp.datalineage.util.SQLUtil;
import com.datasphere.government.gsp.datalineage.util.XML2Model;
import com.datasphere.government.gsp.datalineage.util.XMLUtil;

public class Dlineage
{

	public static final String TABLE_CONSTANT = "CONSTANT";

	private Map<TableMetaData, List<ColumnMetaData>> tableColumns = new HashMap<TableMetaData, List<ColumnMetaData>>( );
	private Pair<procedureImpactResult, List<ProcedureMetaData>> procedures = new Pair<procedureImpactResult, List<ProcedureMetaData>>( new procedureImpactResult( ),
			new ArrayList<ProcedureMetaData>( ) );

	private boolean strict = false;
	private boolean showUIInfo = false;
	private File sqlDir;
	private File[] sqlFiles;
	private String sqlContent;
	private EDbVendor vendor;

	public Dlineage( String sqlContent, EDbVendor vendor, boolean strict,
			boolean showUIInfo )
	{
		this.strict = strict;
		this.showUIInfo = showUIInfo;
		this.vendor = vendor;
		this.sqlFiles = null;
		this.sqlContent = sqlContent;
		tableColumns.clear( );
		procedures = new Pair<procedureImpactResult, List<ProcedureMetaData>>( new procedureImpactResult( ),
				new ArrayList<ProcedureMetaData>( ) );

		String content = sqlContent;
		String database = null;

		database = new DDLParser( tableColumns,
				procedures,
				vendor,
				content.toUpperCase( ),
				strict,
				database ).getDatabase( );

		database = new ViewParser( tableColumns,
				vendor,
				content.toUpperCase( ),
				strict,
				database ).getDatabase( );

		database = new ProcedureRelationScanner( procedures,
				vendor,
				content.toUpperCase( ),
				strict,
				database ).getDatabase( );
	}

	public Dlineage( String[] sqlContents, EDbVendor vendor, boolean strict,
			boolean showUIInfo )
	{
		this.strict = strict;
		this.showUIInfo = showUIInfo;
		this.vendor = vendor;
		this.sqlFiles = null;
		tableColumns.clear( );
		procedures = new Pair<procedureImpactResult, List<ProcedureMetaData>>( new procedureImpactResult( ),
				new ArrayList<ProcedureMetaData>( ) );

		for ( int i = 0; i < sqlContents.length; i++ )
		{
			String content = sqlContents[i];
			String database = null;
			database = new DDLParser( tableColumns,
					procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}

		String database = null;
		for ( int i = 0; i < sqlContents.length; i++ )
		{
			String content = sqlContents[i];
			database = new ViewParser( tableColumns,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}

		database = null;
		for ( int i = 0; i < sqlContents.length; i++ )
		{
			String content = sqlContents[i];
			database = new ProcedureRelationScanner( procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}
	}

	public Dlineage( File[] sqlFiles, EDbVendor vendor, boolean strict,
			boolean showUIInfo )
	{
		this.strict = strict;
		this.showUIInfo = showUIInfo;
		this.vendor = vendor;
		this.sqlFiles = sqlFiles;
		tableColumns.clear( );
		procedures = new Pair<procedureImpactResult, List<ProcedureMetaData>>( new procedureImpactResult( ),
				new ArrayList<ProcedureMetaData>( ) );
		File[] children = sqlFiles;

		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );
			String database = null;
			database = new DDLParser( tableColumns,
					procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}

		String database = null;
		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );
			database = new ViewParser( tableColumns,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}

		database = null;
		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );
			database = new ProcedureRelationScanner( procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}
	}

	public Dlineage( File sqlDir, EDbVendor vendor, boolean strict,
			boolean showUIInfo )
	{
		this.strict = strict;
		this.showUIInfo = showUIInfo;
		this.sqlDir = sqlDir;
		this.vendor = vendor;
		tableColumns.clear( );
		procedures = new Pair<procedureImpactResult, List<ProcedureMetaData>>( new procedureImpactResult( ),
				new ArrayList<ProcedureMetaData>( ) );
		File[] children = listFiles( sqlDir );

		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );

			String database = null;

			database = new DDLParser( tableColumns,
					procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );

		}

		String database = null;
		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );

			database = new ViewParser( tableColumns,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );

		}

		database = null;
		for ( int i = 0; i < children.length; i++ )
		{
			File child = children[i];
			if ( child.isDirectory( ) )
				continue;
			String content = SQLUtil.getFileContent( child );

			database = new ProcedureRelationScanner( procedures,
					vendor,
					content.toUpperCase( ),
					strict,
					database ).getDatabase( );
		}
	}

	public void columnImpact( )
	{
		String result = getColumnImpactResult( false );
		System.out.println( result );
	}

	public String getColumnImpactResult( )
	{
		return getColumnImpactResult( true );
	}

	public String getColumnImpactResult( boolean analyzeDlineage )
	{
		if ( sqlContent == null )
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( );
			Document doc = null;
			Element columnImpactResult = null;
			try
			{
				DocumentBuilder builder = factory.newDocumentBuilder( );
				doc = builder.newDocument( );
				doc.setXmlVersion( "1.0" );
				columnImpactResult = doc.createElement( "columnImpactResult" );
				doc.appendChild( columnImpactResult );
				if ( sqlDir != null && sqlDir.isDirectory( ) )
				{
					Element dirNode = doc.createElement( "dir" );
					dirNode.setAttribute( "name", sqlDir.getAbsolutePath( ) );
					columnImpactResult.appendChild( dirNode );
				}
			}
			catch ( ParserConfigurationException e )
			{
				e.printStackTrace( );
			}

			File[] children = sqlFiles == null ? listFiles( sqlDir ) : sqlFiles;
			for ( int i = 0; i < children.length; i++ )
			{
				File child = children[i];
				if ( child.isDirectory( ) )
					continue;
				if ( child != null )
				{
					Element fileNode = doc.createElement( "file" );
					fileNode.setAttribute( "name", child.getAbsolutePath( ) );
					ColumnImpact impact = new ColumnImpact( fileNode,
							vendor,
							tableColumns,
							strict );
					impact.setDebug( false );
					impact.setShowUIInfo( showUIInfo );
					impact.setTraceErrorMessage( false );
					impact.setAnalyzeDlineage( analyzeDlineage );
					impact.ignoreTopSelect( false );
					impact.impactSQL( );
					if ( impact.getErrorMessage( ) != null
							&& impact.getErrorMessage( ).trim( ).length( ) > 0 )
					{
						System.err.println( impact.getErrorMessage( ).trim( ) );
					}
					if ( fileNode.hasChildNodes( ) )
					{
						columnImpactResult.appendChild( fileNode );
					}
				}
			}
			if ( doc != null )
			{
				try
				{
					return XMLUtil.format( doc, 2 );
				}
				catch ( Exception e )
				{
					e.printStackTrace( );
				}
			}
		}
		else
		{
			ColumnImpact impact = new ColumnImpact( sqlContent,
					vendor,
					tableColumns,
					strict );
			impact.setDebug( false );
			impact.setShowUIInfo( showUIInfo );
			impact.setTraceErrorMessage( false );
			impact.setAnalyzeDlineage( true );
			impact.impactSQL( );
			if ( impact.getErrorMessage( ) != null
					&& impact.getErrorMessage( ).trim( ).length( ) > 0 )
			{
				System.err.println( impact.getErrorMessage( ).trim( ) );
			}
			return impact.getImpactResult( );
		}
		return null;
	}

	public void forwardAnalyze( String tableColumn,
			List<ColumnMetaData[]> relations )
	{
		ColumnMetaData columnMetaData = new MetaScanner( this ).getColumnMetaData( tableColumn );
		List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>( );
		Iterator<TableMetaData> iter = tableColumns.keySet( ).iterator( );
		while ( iter.hasNext( ) )
		{
			columns.addAll( tableColumns.get( iter.next( ) ) );
		}
		if ( columnMetaData != null )
		{
			outputForwardAnalyze( columnMetaData, columns, 0, relations );
		}
	}

	public void backwardAnalyze( String viewColumn,
			List<ColumnMetaData[]> relations )
	{
		ColumnMetaData columnMetaData = new MetaScanner( this ).getColumnMetaData( viewColumn );
		if ( columnMetaData != null )
		{
			outputBackwardAnalyze( columnMetaData, 0, relations );
		}
	}

	private void outputBackwardAnalyze( ColumnMetaData columnMetaData,
			int level, List<ColumnMetaData[]> relations )
	{
		if ( level > 0 )
		{
			for ( int i = 0; i < level; i++ )
			{
				System.out.print( "---" );
			}
			System.out.print( ">" );
		}
		System.out.println( columnMetaData.getDisplayFullName( ) );
		if ( columnMetaData.getReferColumns( ) != null
				&& columnMetaData.getReferColumns( ).length > 0 )
		{
			for ( int i = 0; i < columnMetaData.getReferColumns( ).length; i++ )
			{
				ColumnMetaData sourceColumn = columnMetaData.getReferColumns( )[i];
				if ( containsRelation( columnMetaData, sourceColumn, relations ) )
				{
					outputBackwardAnalyze( columnMetaData.getReferColumns( )[i],
							level + 1,
							relations );
				}
			}
		}
	}

	private boolean containsRelation( ColumnMetaData targetColumn,
			ColumnMetaData sourceColumn, List<ColumnMetaData[]> relations )
	{
		if ( relations == null )
			return false;
		for ( int i = 0; i < relations.size( ); i++ )
		{
			ColumnMetaData[] relation = relations.get( i );
			if ( relation[0].equals( targetColumn )
					&& relation[1].equals( sourceColumn ) )
				return true;
		}
		return false;
	}

	private void outputForwardAnalyze( ColumnMetaData columnMetaData,
			List<ColumnMetaData> columns, int level,
			List<ColumnMetaData[]> relations )
	{
		if ( level > 0 )
		{
			for ( int i = 0; i < level; i++ )
			{
				System.out.print( "---" );
			}
			System.out.print( ">" );
		}
		System.out.println( columnMetaData.getDisplayFullName( ) );
		for ( int i = 0; i < columns.size( ); i++ )
		{
			ColumnMetaData targetColumn = columns.get( i );
			if ( Arrays.asList( targetColumn.getReferColumns( ) )
					.contains( columnMetaData ) )
			{
				if ( containsRelation( targetColumn, columnMetaData, relations ) )
				{
					outputForwardAnalyze( targetColumn,
							columns,
							level + 1,
							relations );
				}
			}
		}
	}

	public void outputDDLSchema( )
	{
		System.out.println( new DDLSchema( tableColumns ).getSchemaXML( ) );
	}

	public database[] getDataMetaInfos( )
	{
		return new DDLSchema( tableColumns ).getDataMetaInfos( );
	}

	private File[] listFiles( File sqlFiles )
	{
		List<File> children = new ArrayList<File>( );
		if ( sqlFiles != null )
			listFiles( sqlFiles, children );
		return children.toArray( new File[0] );
	}

	private void listFiles( File rootFile, List<File> children )
	{
		if ( rootFile.isFile( ) )
			children.add( rootFile );
		else
		{
			File[] files = rootFile.listFiles( );
			for ( int i = 0; i < files.length; i++ )
			{
				listFiles( files[i], children );
			}
		}
	}

	public columnImpactResult generateColumnImpact( StringBuilder errorMessage )
	{
		PrintStream systemSteam = System.err;
		ByteArrayOutputStream sw = new ByteArrayOutputStream( );
		PrintStream pw = new PrintStream( sw );
		System.setErr( pw );

		String impactResult = this.getColumnImpactResult( );

		pw.close( );
		System.setErr( systemSteam );

		if ( errorMessage != null )
		{
			errorMessage.append( new String( sw.toByteArray( ) ).trim( ) );
		}

		return getColumnImpactResult( impactResult );
	}

	private columnImpactResult getColumnImpactResult( String result )
	{
		try
		{
			String[] results = result.split( "\n" );
			StringBuilder buffer = new StringBuilder( );
			for ( int i = 0; i < results.length; i++ )
			{
				String line = results[i];
				if ( line.indexOf( "columnImpactResult" ) != -1
						|| line.indexOf( "targetColumn" ) != -1
						|| line.indexOf( "sourceColumn" ) != -1
						|| line.indexOf( "linkTable" ) != -1 )
				{
					buffer.append( line ).append( "\n" );
				}
			}
			return XML2Model.loadXML( columnImpactResult.class,
					buffer.toString( ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
		return null;
	}

	public static void main( String[] args )
	{
		args = new String[10];
		args[0] =  "/Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/GeneralSQLParser/datalliance/sql/sqlfile.sql";

		File sqlFiles = new File("/Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/GeneralSQLParser/datalliance/sql/sqlfile.sql");


		String sqls = "create PROCEDURE PROC_UPDATE_COMPOSITERATING AS\n" +
				"  CUR_MONTH NUMBER;\n" +
				"  CURSOR CUR IS\n" +
				"    SELECT -1 FROM DUAL UNION SELECT -2 FROM DUAL UNION SELECT -3 FROM DUAL UNION SELECT -6 FROM DUAL;\n" +
				"BEGIN\n" +
				"  EXECUTE IMMEDIATE 'DELETE FROM RPT_COMPOSITERATING';\n" +
				"  OPEN CUR;\n" +
				"  LOOP\n" +
				"    FETCH CUR\n" +
				"      INTO CUR_MONTH;\n" +
				"    EXIT WHEN CUR%NOTFOUND;\n" +
				"\n" +
				"    INSERT INTO RPT_COMPOSITERATING\n" +
				"      (ID,\n" +
				"       SECUCODE,\n" +
				"       TRADINGCODE,\n" +
				"       SECUABBR,\n" +
				"       STATISTICDATE,\n" +
				"       RATINGAGENCYNUM,\n" +
				"       BUYAGENCYNUM,\n" +
				"       INCREASEAGENCYNUM,\n" +
				"       NEUTRALAGENCYNUM,\n" +
				"       REDUCEAGENCYNUM,\n" +
				"       SELLAGENCYNUM,\n" +
				"       HIGHESTPRICE,\n" +
				"       LOWESTPRICE,\n" +
				"       AVGPRICE,\n" +
				"       MONTH,\n" +
				"       ENTRYTIME,\n" +
				"       UPDATETIME,\n" +
				"       GROUNDTIME,\n" +
				"       UPDATEID,\n" +
				"       RESOURCEID,\n" +
				"       RECORDID,\n" +
				"       PUBDATE)\n" +
				"      SELECT SEQ_ID.NEXTVAL,\n" +
				"             A.SECUCODE,\n" +
				"             A.TRADINGCODE,\n" +
				"             A.SECUABBR,\n" +
				"             TRUNC(SYSDATE) STATISTICDATE,\n" +
				"             A.RATINGAGENCYNUM,\n" +
				"             NVL(A.BUYAGENCYNUM, 0) AS BUYAGENCYNUM,\n" +
				"             NVL(A.INCREASEAGENCYNUM, 0) AS NCREASEAGENCYNUM,\n" +
				"             NVL(A.NEUTRALAGENCYNUM, 0) AS NEUTRALAGENCYNUM,\n" +
				"             NVL(A.REDUCEAGENCYNUM, 0) AS REDUCEAGENCYNUM,\n" +
				"             NVL(A.SELLAGENCYNUM, 0) AS SELLAGENCYNUM,\n" +
				"             NVL(B.HIGHESTPRICE, 0) AS HIGHESTPRICE,\n" +
				"             NVL(B.LOWESTPRICE, 0) AS LOWESTPRICE,\n" +
				"             NVL(B.AVGPRICE, 0) AS AVGPRICE,\n" +
				"             CUR_MONTH*-1 AS MONTH,\n" +
				"             SYSDATE,\n" +
				"             SYSDATE,\n" +
				"             SYSDATE,\n" +
				"             SEQ_ID.NEXTVAL,\n" +
				"             SEQ_ID.NEXTVAL,\n" +
				"             SEQ_ID.NEXTVAL,\n" +
				"             PUBDATE\n" +
				"        FROM (SELECT SECUCODE,\n" +
				"                     TRADINGCODE,\n" +
				"                     SECUABBR,\n" +
				"                     SUM(C) RATINGAGENCYNUM,\n" +
				"                     SUM(DECODE(INVRATINGCODE, 1, C)) BUYAGENCYNUM,\n" +
				"                     SUM(DECODE(INVRATINGCODE, 2, C)) INCREASEAGENCYNUM,\n" +
				"                     SUM(DECODE(INVRATINGCODE, 3, C)) NEUTRALAGENCYNUM,\n" +
				"                     SUM(DECODE(INVRATINGCODE, 4, C)) REDUCEAGENCYNUM,\n" +
				"                     SUM(DECODE(INVRATINGCODE, 5, C)) SELLAGENCYNUM\n" +
				"                FROM (SELECT a.SECUCODE,\n" +
				"                             a.TRADINGCODE,\n" +
				"                             b.SECUABBR,\n" +
				"                             INVRATINGCODE,\n" +
				"                             COUNT(*) C\n" +
				"                        FROM TEXT_FORECASTRATING a\n" +
				"                        JOIN pub_securitiesmain b ON a.secucode = b.secucode\n" +
				"                       WHERE TRUNC(PUBDATE) >= ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)\n" +
				"                         AND a.SECUCODE != 0\n" +
				"                         AND INVRATINGCODE IN (1, 2, 3, 4, 5)\n" +
				"                       GROUP BY a.SECUCODE,\n" +
				"                                a.TRADINGCODE,\n" +
				"                                b.SECUABBR,\n" +
				"                                INVRATINGCODE)\n" +
				"               GROUP BY SECUCODE, TRADINGCODE, SECUABBR) A,\n" +
				"             (SELECT SECUCODE,MAX(T.PUBDATE) AS PUBDATE,\n" +
				"                     MAX(T.INDEXVAL) HIGHESTPRICE,\n" +
				"                     MIN(T.INDEXVAL) LOWESTPRICE,\n" +
				"                     TRUNC(AVG(T.INDEXVAL), 2) AVGPRICE\n" +
				"                FROM TEXT_PERFORMANCEFORECAST T\n" +
				"               WHERE T.PUBDATE > ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)\n" +
				"                 AND T.INDEXCODE = 1190\n" +
				"               GROUP BY SECUCODE) B\n" +
				"       WHERE A.SECUCODE = B.SECUCODE(+);\n" +
				"\n" +
				"  END LOOP;\n" +
				"  CLOSE CUR;\n" +
				"  COMMIT;\n" +
				"END PROC_UPDATE_COMPOSITERATING;\n";

		sqls = sqls.toLowerCase();

//		System.out.println(sqls);

		List<String> argList = Arrays.asList( args );

		if ( argList.indexOf( "/f" ) != -1
				&& argList.size( ) > argList.indexOf( "/f" ) + 1 )
		{
			sqlFiles = new File( args[argList.indexOf( "/f" ) + 1] );
			if ( !sqlFiles.exists( ) || !sqlFiles.isFile( ) )
			{
				System.out.println( sqlFiles + " is not a valid file." );
				return;
			}
		}
		else if ( argList.indexOf( "/d" ) != -1
				&& argList.size( ) > argList.indexOf( "/d" ) + 1 )
		{
			sqlFiles = new File( args[argList.indexOf( "/d" ) + 1] );
			if ( !sqlFiles.exists( ) || !sqlFiles.isDirectory( ) )
			{
				System.out.println( sqlFiles + " is not a valid directory." );
				return;
			}
		}


		EDbVendor vendor = EDbVendor.dbvoracle;

		int index = argList.indexOf( "/t" );

		if ( index != -1 && args.length > index + 1 )
		{
			if ( args[index + 1].equalsIgnoreCase( "mssql" ) )
			{
				vendor = EDbVendor.dbvmssql;
			}
			else if ( args[index + 1].equalsIgnoreCase( "db2" ) )
			{
				vendor = EDbVendor.dbvdb2;
			}
			else if ( args[index + 1].equalsIgnoreCase( "mysql" ) )
			{
				vendor = EDbVendor.dbvmysql;
			}
			else if ( args[index + 1].equalsIgnoreCase( "netezza" ) )
			{
				vendor = EDbVendor.dbvnetezza;
			}
			else if ( args[index + 1].equalsIgnoreCase( "teradata" ) )
			{
				vendor = EDbVendor.dbvteradata;
			}
			else if ( args[index + 1].equalsIgnoreCase( "oracle" ) )
			{
				vendor = EDbVendor.dbvoracle;
			}
			else if ( args[index + 1].equalsIgnoreCase( "informix" ) )
			{
				vendor = EDbVendor.dbvinformix;
			}
			else if ( args[index + 1].equalsIgnoreCase( "sybase" ) )
			{
				vendor = EDbVendor.dbvsybase;
			}
			else if ( args[index + 1].equalsIgnoreCase( "postgresql" ) )
			{
				vendor = EDbVendor.dbvpostgresql;
			}
			else if ( args[index + 1].equalsIgnoreCase( "hive" ) )
			{
				vendor = EDbVendor.dbvhive;
			}
			else if ( args[index + 1].equalsIgnoreCase( "greenplum" ) )
			{
				vendor = EDbVendor.dbvgreenplum;
			}
			else if ( args[index + 1].equalsIgnoreCase( "redshift" ) )
			{
				vendor = EDbVendor.dbvredshift;
			}
		}

		boolean strict = argList.indexOf( "/s" ) != -1;
		boolean log = argList.indexOf( "/log" ) != -1;
		PrintStream pw = null;
		ByteArrayOutputStream sw = null;
		PrintStream systemSteam = System.err;

		try
		{
			sw = new ByteArrayOutputStream( );
			pw = new PrintStream( sw );
			System.setErr( pw );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}

		Dlineage dlineage = new Dlineage( sqls, vendor, true, false );

		boolean forwardAnalyze = argList.indexOf( "/fo" ) != -1;
		boolean backwardAnalyze = argList.indexOf( "/b" ) != -1;
		boolean outputDDL = argList.indexOf( "/ddl" ) != -1;

		if ( !forwardAnalyze && !backwardAnalyze && !outputDDL )
		{
			dlineage.columnImpact( );
		}
		else if ( outputDDL )
		{
			dlineage.outputDDLSchema( );
		}
		else
		{
			DlineageRelation relation = new DlineageRelation( );
			columnImpactResult impactResult = relation.generateColumnImpact( dlineage,
					null );

			List<ColumnMetaData[]> relations = relation.collectDlineageRelations( dlineage,
					impactResult );

			List<ColumnMetaData[]> aa = relation.collectDlineageRelations(dlineage, impactResult);
			aa.isEmpty();
			if ( forwardAnalyze
					&& argList.size( ) > argList.indexOf( "/fo" ) + 1 )
			{
				String tableColumn = argList.get( argList.indexOf( "/fo" ) + 1 );
				dlineage.forwardAnalyze( tableColumn, relations );
			}

			if ( backwardAnalyze
					&& argList.size( ) > argList.indexOf( "/b" ) + 1 )
			{
				String viewColumn = argList.get( argList.indexOf( "/b" ) + 1 );
				dlineage.backwardAnalyze( viewColumn, relations );
			}
		}

		if ( pw != null )
		{
			pw.close( );
		}

		if ( sw != null )
		{
			String errorMessage = sw.toString( ).trim( );
			if ( errorMessage.length( ) > 0 )
			{
				if ( log )
				{
					try
					{
						pw = new PrintStream( new File( ".", "dlineage.log" ) );
						pw.print( errorMessage );
					}
					catch ( FileNotFoundException e )
					{
						e.printStackTrace( );
					}
				}

				System.setErr( systemSteam );
				System.err.println( errorMessage );
			}
		}
	}

	public Map<TableMetaData, List<ColumnMetaData>> getMetaData( )
	{
		return tableColumns;
	}

	public Pair<procedureImpactResult, List<ProcedureMetaData>> getProcedures( )
	{
		return procedures;
	}

	public boolean isStrict( )
	{
		return strict;
	}

	public EDbVendor getVendor( )
	{
		return vendor;
	}

	class SecurityAccess {
		public void disopen() {

		}
	}
}
