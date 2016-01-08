/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/20
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model;

import java.util.List;

import jp.go.jsps.kaken.model.exceptions.ConvertException;
import jp.go.jsps.kaken.util.FileResource;


/**
 * ファイルの変換を行うインターフェース。
 * 
 * ID RCSfile="$RCSfile: IConverter.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:50 $"
 */
public interface IConverter {

	/**
	 * 設定情報に基づきIODファイルを作成する。
	 * @param iodSettingInfo		設定情報リスト
	 * @return						作成したIODファイルリソース
	 * @throws ConvertException		作成したファイルの取得に失敗したとき。
	 */
	public FileResource iodFileCreation(List iodSettingInfo) throws ConvertException;

	/**
	 * 指定ファイルをIODファイルに変換する。
	 * @param attachedResource		添付ファイルリソース
	 * @return						変換したIODファイルリソース
	 * @throws ConvertException	変換中に例外が発生したとき。
	 */
	public FileResource iodFileCreation(FileResource attachedResource) throws ConvertException;

	/**
	 * iodファイルリソースを結合し、パスワード付PDFリソースを取得する。
	 * @param iodResources			結合するファイルリソース(iodファイル)。
	 * @param password				PDFを開くためのパスワード。	
	 * @param jokyoId				申請状況ID
	 * @param jigyoKbn				事業区分
	 * @return						結合したPDFファイルリソース
	 * @throws ConvertException	変換中に例外が発生したとき。
	 */
	public FileResource iodToPdf(List iodFileResources, String password, String jokyoId, String jigyoKbn) throws ConvertException;


	//2005.07.15 iso PDF添付機能追加
	/**
	 * PDFファイルが有効かを調べる。
	 * @param fileRes					調査するファイルリソース。
	 * @return							有効：0　無効:エラーコード
	 * @throws ConvertException		変換中に例外が発生したとき。
	 */
	public int checkPdf(FileResource fileRes) throws ConvertException;
}