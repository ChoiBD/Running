
public class SetDAO extends DBConn{

	private SetDAO() {	}
	
	public static SetDAO getInstance() {
		return new SetDAO();
	}
	
//	ID(FK)	varChar2(15)	ȸ�����̵�(FK)	NULL	N/A
//	bg_r	NUMBER	���_R	NULL	N/A
//	bg_g	NUMBER	���_G	NULL	N/A
//	bg_b	NUMBER	���_B	NULL	N/A
//	fg_r	NUMBER	���ڻ�_R	NULL	N/A
//	fg_g	NUMBER	���ڻ�_G	NULL	N/A
//	fg_b	NUMBER	���ڻ�_B	NULL	N/A
//	bbg_r	NUMBER	��ư��_R	NULL	N/A
//	bbg_g	NUMBER	��ư��_G	NULL	N/A
//	bbg_b	NUMBER	��ư��_B	NULL	N/A
//	bfg_r	NUMBER	��ư����_R	NULL	N/A
//	bfg_g	NUMBER	��ư����_G	NULL	N/A
//	bfg_b	NUMBER	��ư����_B	NULL	N/A
//	font	varchar2(10)	��Ʈ	NULL	N/A

	// ���� �������� dao
	public SetVO getSetting(String id) {
		SetVO vo = new SetVO();
		try {
			getConnect();
			String sql = "select id, bg_r, bg_g, bg_b, fg_r, fg_g, fg_b, "
										+ "bbg_r, bbg_g, bbg_b, bfg_r, bfg_g, bfg_b, font, b_font  from p_set where id=?";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, id);
			
			rs = prs.executeQuery();
			rs.next();
			
			vo = new SetVO(rs.getString(1), rs.getInt(2), rs.getInt(3),rs.getInt(4),
															rs.getInt(5), rs.getInt(6),rs.getInt(7),
															rs.getInt(8), rs.getInt(9),rs.getInt(10),
															rs.getInt(11), rs.getInt(12),rs.getInt(13),	
															rs.getString(14), rs.getString(15));
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {disConnect();}
		return vo;
	}
	
	// ���� �Է��ϴ� dao == ȸ�� ���� ��
	public int insert(SetVO vo) {
		int result =0 ;
		try {
			getConnect();
			
			String sql = "insert into p_set values(?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?)";
			prs = con.prepareStatement(sql);
			
			prs.setString(1, vo.getId());
			prs.setInt(2, vo.getBgR());	prs.setInt(3, vo.getBgG());	prs.setInt(4, vo.getBgB());
			prs.setInt(5, vo.getFgR());	prs.setInt(6, vo.getFgG());	prs.setInt(7, vo.getFgB());
			prs.setInt(8, vo.getBbgR());	prs.setInt(9, vo.getBbgG());	prs.setInt(10, vo.getBbgB());
			prs.setInt(11, vo.getBfgR());	prs.setInt(12, vo.getBfgG());	prs.setInt(13, vo.getBfgB());
			prs.setString(14, vo.getFont()); prs.setString(15, vo.getbFont());
								
			result = prs.executeUpdate();	
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
	
	// ���� �����ϴ� dao == ���� ���� ��
	public int modify(SetVO vo) {
		int result =0 ;
		try {
			getConnect();
			
			String sql = "update p_set set bg_r = ?, bg_g = ?, bg_b =?, fg_r=?, fg_g=?, fg_b=?,"	+
														"bbg_r =?, bbg_g = ?, bbg_b=?, bfg_r=?, bfg_g=?, bfg_b=?,"	+
														" font =?, b_font =? where id =? ";
			prs = con.prepareStatement(sql);
			
			prs.setString(15, vo.getId());
			prs.setInt(1, vo.getBgR());	prs.setInt(2, vo.getBgG());	prs.setInt(3, vo.getBgB());
			prs.setInt(4, vo.getFgR());	prs.setInt(5, vo.getFgG());	prs.setInt(6, vo.getFgB());
			prs.setInt(7, vo.getBbgR());	prs.setInt(8, vo.getBbgG());	prs.setInt(9, vo.getBbgB());
			prs.setInt(10, vo.getBfgR());	prs.setInt(11, vo.getBfgG());	prs.setInt(12, vo.getBfgB());
			prs.setString(13, vo.getFont()); prs.setString(14, vo.getbFont());
								
			result = prs.executeUpdate();	
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally { disConnect();}
		return result;
	}
}
