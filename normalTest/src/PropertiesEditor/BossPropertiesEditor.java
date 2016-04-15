package PropertiesEditor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BossPropertiesEditor extends PropertyEditorSupport {

	private BossBeanInfo boss;

	@Override
	public void setAsText(String paramString) throws IllegalArgumentException {
		if (paramString == null || paramString.equals(""))
			return;
		String[] attrs = paramString.split(",");
		String name = attrs[0];
		boss = new BossBeanInfo();
		boss.setName(name);
		boss.setAge(Integer.parseInt(attrs[1]));
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			boss.setCompany(new Company(dateFormater.parse(attrs[2]), attrs[3], attrs[4]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setValue(boss);
	}

	public BossBeanInfo getBoss() {
		return boss;
	}

	public void setBoss(BossBeanInfo boss) {
		this.boss = boss;
	}

	public static void main(String args[]) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (args == null||args.length<1) {
			System.err
					.println("no valid information configurated, plesae config like: Paul,30,2017-10-01,CPW corp,Nanjing Jiangsu China");
			System.exit(0);
		}
		try {
			
			PropertyDescriptor titlePositionDescriptor = new PropertyDescriptor("boss", BossPropertiesEditor.class);
			titlePositionDescriptor.setPropertyEditorClass(BossPropertiesEditor.class);
			titlePositionDescriptor.getPropertyEditorClass();
			BossPropertiesEditor bpe = new BossPropertiesEditor();
			titlePositionDescriptor.getWriteMethod().invoke(bpe, "Paul,30,2017-10-01,CPW corp,Nanjing Jiangsu China");
			System.out.println(bpe);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
