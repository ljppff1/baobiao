package com.soloman.org.cn.ui.indent;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soloman.org.cn.R;
import com.soloman.org.cn.bean.Bodyguard;
import com.soloman.org.cn.bean.Indent;
import com.soloman.org.cn.bean.User;
import com.soloman.org.cn.http.HttpRestClient;
import com.soloman.org.cn.http.JsonHttpResponseHandler;
import com.soloman.org.cn.http.RequestParams;

/**
 * 评论
 * 
 * @author Mac
 * 
 */
public class ActComment extends Activity
{
	private ArrayList<String> Commented_bodyguards;
	// 用来控制CheckBox的选中状况
	private ArrayList<String> isSelected;
	int i = -1;
	private ArrayList<Bodyguard> lt;
	private ImageView act_iv_viewss;
	private TextView act_tv_addresss;
	private RatingBar act_rb_comment;
	private EditText item_tv_common_address_text;
	private ListView xListView;
	private RadioButton rb_gchat;
	private CommentAdapter comment;
	StringBuffer sb;
	private String order_id;
	private String bodyguard_ids;
	private String content;
	private String mark;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_comment);
		Bundle bundle = getIntent().getExtras();
		lt = (ArrayList<Bodyguard>) bundle.getSerializable("bodyguard");
		Commented_bodyguards=(ArrayList<String>) bundle.getSerializable("Commented_bodyguards");
		order_id = bundle.getString("id");
		setupView();
		setupListener();
	}

	private void setupView()
	{
		// TODO Auto-generated method stub
		isSelected = new ArrayList<String>();
		act_iv_viewss = (ImageView) findViewById(R.id.act_iv_viewss);
		act_tv_addresss = (TextView) findViewById(R.id.act_tv_addresss);
		act_rb_comment = (RatingBar) findViewById(R.id.act_rb_comment);
		item_tv_common_address_text = (EditText) findViewById(R.id.item_tv_common_address_text);
		xListView = (ListView) findViewById(R.id.xListView);
		rb_gchat = (RadioButton) findViewById(R.id.rb_gchat);
		comment = new CommentAdapter(lt, this);
		xListView.setAdapter(comment);
	}

	private void setupListener()
	{
		// TODO Auto-generated method stub
		act_tv_addresss.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub

				if (item_tv_common_address_text.getText().length() > 0
						&& isSelected.size() > 0)
				{
					sb = new StringBuffer("[");
					int is = isSelected.size() - 1;
					for (int i = 0; i < isSelected.size(); i++)
					{
						sb.append(isSelected.get(i) + ",");
						if (is == i)
						{
							sb.append("]");
						}
					}
					bodyguard_ids = sb.toString().substring(0,
							sb.indexOf("]") - 1)
							+ "]";
					mark = String.valueOf(act_rb_comment.getRating())
							.substring(0, 1);
					content = item_tv_common_address_text.getText().toString();
					comment();
				} else
				{
					return;
				}
			}
		});
		rb_gchat.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (i == -1)
				{
					isSelected.clear();
					rb_gchat.setChecked(true);
					for (int i = 0; i < lt.size(); i++)
					{
						isSelected.add(lt.get(i).getId() + "");
						lt.get(i).setIs(true);
					}
					i = 1;
				} else
				{
					isSelected.clear();
					rb_gchat.setChecked(false);
					for (int i = 0; i < lt.size(); i++)
					{
						lt.get(i).setIs(false);
					}
					i = -1;
				}
				comment.notifyDataSetChanged();
			}
		});
	}

	class CommentAdapter extends BaseAdapter
	{
		private ArrayList<Bodyguard> lt;

		private Context context;

		public CommentAdapter(ArrayList<Bodyguard> lt, Context context)
		{
			super();
			this.lt = lt;
			this.context = context;

		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return lt.size();
		}

		@Override
		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return lt.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2)
		{
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_comment, null);
				holder.item_tv_comment_name = (TextView) convertView
						.findViewById(R.id.item_tv_comment_name);
				holder.item_iv_ = (ImageView) convertView
						.findViewById(R.id.item_iv_);
				holder.item_tv_comment_names = (TextView) convertView
						.findViewById(R.id.item_tv_comment_names);
				holder.item_rl_click = (RelativeLayout) convertView
						.findViewById(R.id.item_rl_click);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			final Bodyguard body = lt.get(position);
			holder.item_tv_comment_name.setText(body.getName());
			if (body.getIs() == true)
			{
				holder.item_iv_.setVisibility(View.VISIBLE);
			} else
			{
				holder.item_iv_.setVisibility(View.GONE);
			}
			holder.item_rl_click.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if (body.getIs() == false)
					{
						body.setIs(true);
						holder.item_iv_.setVisibility(View.VISIBLE);
						isSelected.add(body.getId() + "");
						System.out.println(body.getId() + "添加");
					} else
					{
						for (int i = 0; i < isSelected.size(); i++)
						{
							if (isSelected.get(i).equals(body.getId() + ""))
							{
								body.setIs(false);
								holder.item_iv_.setVisibility(View.GONE);
								isSelected.remove(i);
							}
						}
						System.out.println(body.getId() + "删除");
						for (int i = 0; i < isSelected.size(); i++)
						{
							System.out.println(isSelected.get(i));
						}
					}
				}
			});
			return convertView;
		}

		class ViewHolder
		{
			TextView item_tv_comment_name;
			ImageView item_iv_;
			TextView item_tv_comment_names;
			RelativeLayout item_rl_click;
		}
	}

	/**
	 * 评论
	 */
	private void comment()
	{
		// boolean is = false;
		// if (NetworkJudge.getNetWorkType(getActivity()) !=
		// NetworkJudge.NETWORKTYPE_INVALID)
		// {
		// is = true;
		// }
		RequestParams requestParams = new RequestParams();
		requestParams.put("order_id", order_id);
		requestParams.put("bodyguard_ids[]", bodyguard_ids);
		requestParams.put("content", content);
		requestParams.put("mark", mark);

		HttpRestClient.postHttpHuaShangha(this, "comments/create", "v2",
				requestParams, new JsonHttpResponseHandler()
				{
					@Override
					public void onSuccess(JSONObject response)
					{
						// TODO Auto-generated method stub
						super.onSuccess(response);
						try
						{
							if (response.getInt("code") == 200)
							{
								Toast.makeText(ActComment.this, "评论成功!",
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable e, JSONObject errorResponse)
					{
						// TODO Auto-generated method stub
						super.onFailure(e, errorResponse);
						Toast.makeText(ActComment.this, "未知错误请联系管理员：错误码111",
								Toast.LENGTH_LONG).show();
					}
				});

	}
}
